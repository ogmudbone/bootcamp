package com.anotheria.bootcamp.file_transfer.server.files;

import com.anotheria.bootcamp.file_transfer.Token;

import java.util.*;

public class FilesLock {

    private final static long TOKEN_LIVE_TIME = 5 * 1000 * 60; // 5 minutes
    private final static long CLEANUP_INTERVAL = 5 * 1000 * 60; // 5 minutes

    private static final LockCleanupThread cleanupThread = new LockCleanupThread();

    private final Map<Token, LockData> locks = new HashMap<>();

    private static void initCleanupThread(){

        synchronized (cleanupThread) {
            if(!cleanupThread.isAlive()) {
                cleanupThread.start();
            }
        }

    }

    private static void checkCleanupThreadStop(){
        if(cleanupThread.isAlive()){

            if(cleanupThread.locks.size() == 0)
                cleanupThread.interrupt();

        }
    }

    private Optional<LockData> getLockDataForFile(String fileName){

        synchronized (locks){

            return locks.values().stream().filter(
                    lockData -> Objects.equals(lockData.fileName, fileName)
            ).findAny();

        }

    }

    public FilesLock(){
        synchronized (cleanupThread) {
            initCleanupThread();
            cleanupThread.registerLockManager(this);
        }
    }

    private boolean checkLockAccess(String fileName, LockType type){

        Object[] types = locks.values().stream()
                .filter(lockData -> lockData.fileName.equals(fileName))
                .map(lockData -> lockData.lockType)
                .toArray();

        if(types instanceof LockType[]) {

            LockType[] lockTypes = ((LockType[]) types);

            for (LockType lockType : lockTypes)
                if (!lockType.allows(type))
                    return false;

            return true;

        }
        else
            return true;

    }

    /**
     *  Locks file for file transfer task.
     *  Non-locked file can be locket on read and write,
     *  read-locked file can be locked for read by another task
     *  write-locked file can`t be locked for another task
     *
     * @param fileName name of file to lock
     * @param token token of file transfer task
     * @param type lock type (READ or WRITE)
     */
    public void setLock(String fileName, Token token, LockType type){

        synchronized (locks) {

            if (checkLockAccess(fileName, type)) {

                LockData newLock = new LockData();
                newLock.fileName = fileName;
                newLock.lockType = type;

                locks.put(token, newLock);

            }

        }

    }

    /**
     *
     * @param fileName name of file to request lock
     * @return lock type of current file,
     *          if it`s not locked - returns LOCK_NONE
     */
    public LockType getLockForFile(String fileName){

        synchronized (locks) {

            return getLockDataForFile(fileName)
                    .map(lockData -> lockData.lockType)
                    .orElse(LockType.LOCK_NONE);

        }

    }

    /**
     * Prevent cleaning up of lock for token.
     * @param token token of lock to protect from cleaning up
     */
    public void extendLockForToken(Token token){

        synchronized (locks) {

            if (locks.containsKey(token)) {
                locks.get(token).isExtended = true;
            }

        }

    }

    /**
     * Releases lock, that was given for
     * this token
     * @param token token of lock
     */
    public void releaseLock(Token token){

        synchronized (locks){
            locks.remove(token);
        }

    }

    private void cleanup(){

        synchronized (locks) {

            final long cleanStartTime = System.currentTimeMillis();

            for (Token token : locks.keySet())
                if (locks.get(token).created + TOKEN_LIVE_TIME < cleanStartTime && !locks.get(token).isExtended)
                    locks.remove(token);

        }

    }

    public void close(){
        cleanupThread.removeLockManager(this);
        checkCleanupThreadStop();
    }

    public void finalize(){
        close();
    }

    private class LockData{

        private String fileName;
        private boolean isExtended = false;
        private LockType lockType = LockType.LOCK_NONE;
        private long created;

        private LockData(){
            this.created = System.currentTimeMillis();
        }

    }

    public enum LockType{

        LOCK_NONE{
            public boolean allows(LockType other){
                return true;
            }
        },
        LOCK_READ{
            public boolean allows(LockType other){
                return other == LOCK_READ;
            }
        },
        LOCK_WRITE{
            public boolean allows(LockType other){
                return false;
            }
        };

        public abstract boolean allows(LockType other);

    }

    private static class LockCleanupThread extends Thread{

        private List<FilesLock> locks = new LinkedList<>();

        private void registerLockManager(FilesLock lock){
            locks.add(lock);
        }

        private void removeLockManager(FilesLock lock){
            locks.remove(lock);
        }

        public void run(){

            try {

                while(!this.isInterrupted()){
                    sleep(CLEANUP_INTERVAL);
                    synchronized (this) {
                        for (FilesLock lock : locks)
                            lock.cleanup();
                    }
                }

            } catch (InterruptedException ignored) {}

        }

    }

}
