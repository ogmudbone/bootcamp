import com.anotheria.bootcamp.arguments_parser.AddressAndPort;
import com.anotheria.bootcamp.arguments_parser.ArgumentsParser;
import com.anotheria.bootcamp.arguments_parser.ArgumentsParserBuilder;
import com.anotheria.bootcamp.arguments_parser.InvalidArgumentException;
import com.anotheria.bootcamp.arguments_parser.parsers.AddressAndPortValidator;
import com.anotheria.bootcamp.arguments_parser.parsers.FilePathValidator;
import com.anotheria.bootcamp.file_transfer.client.Client;

public class Main {

    public static void main(String[] args){

        ArgumentsParser argsParser = null;

        try {
            argsParser = new ArgumentsParserBuilder()
                    .addParser(0, new AddressAndPortValidator())
                    .addParser(1, new FilePathValidator())
                    .setArgs(args).build();
        } catch (InvalidArgumentException e) {
            System.out.println("Invalid arguments supplied for connection");
            System.out.println(e.getMessage());
            System.exit(1);
        }

        AddressAndPort addressAndPort = argsParser.get(0, AddressAndPort.class)
                .orElse(new AddressAndPort("bad.day", 666));
        String folderPath = argsParser.get(1, String.class)
                .orElse("BAD_DAY(");

        new Client(addressAndPort.getAddress(), addressAndPort.getPort(), folderPath).start();


    }

}
