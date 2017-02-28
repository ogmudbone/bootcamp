import com.anotheria.bootcamp.arguments_parser.AddressAndPort;
import com.anotheria.bootcamp.arguments_parser.ArgumentsParser;
import com.anotheria.bootcamp.arguments_parser.ArgumentsParserBuilder;
import com.anotheria.bootcamp.arguments_parser.InvalidArgumentException;
import com.anotheria.bootcamp.arguments_parser.parsers.AddressAndPortValidator;
import com.anotheria.bootcamp.arguments_parser.parsers.FilePathValidator;
import com.anotheria.bootcamp.file_transfer.server.Server;

public class Main {

    public static void main(String[] args){

        ArgumentsParser argsParser = null;

        try {
            argsParser = new ArgumentsParserBuilder()
                    .addParser(0, new AddressAndPortValidator())
                    .addParser(1, new AddressAndPortValidator())
                    .addParser(2, new FilePathValidator())
                    .setArgs(args).build();
        } catch (InvalidArgumentException e) {
            System.out.println("Invalid arguments supplied for connection");
            System.out.println(e.getMessage());
            System.exit(1);
        }

        AddressAndPort commandAddressAndPort = argsParser.get(0, AddressAndPort.class)
                .orElse(new AddressAndPort("bad.day", 666));
        AddressAndPort transferAddressAndPort = argsParser.get(1, AddressAndPort.class)
                .orElse(new AddressAndPort("bad.day", 666));
        String folderPath = argsParser.get(2, String.class)
                .orElse("BAD_DAY(");

        new Server(commandAddressAndPort.getAddress(), commandAddressAndPort.getPort(),
                transferAddressAndPort.getAddress(), transferAddressAndPort.getPort(),
                folderPath).start();

    }

}
