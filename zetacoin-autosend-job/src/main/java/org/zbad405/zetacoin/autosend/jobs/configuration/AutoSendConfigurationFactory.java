package org.zbad405.zetacoin.autosend.jobs.configuration;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author Gilles Cadignan
 */
public class AutoSendConfigurationFactory {

    //Configuration file keys
    public static final String WALLET_SOURCE_URL_KEY = "sourceWallet";
    public static final String WALLET_DESTINATION_URL_KEY = "addDestinationWallet";
    public static final String WALLET_DESTINATION_TRESHOLD_KEY = "destinationWalletTreshold";
    public static final String BATCH_SIZE_KEY = "batchSize";
    public static final String AUTOSEND_DELAY = "autosendDelay";
    public static final String WALLET_SOURCE_MINIMUM_BALANCE_KEY = "sourceWalletMinimumBalance";


    /**
     * path to configuration file, default path is
     * ./autosend.conf
     */
    private String filePath = "./autosend.conf";

    /**
     * constructor providing configuration file path
     * @param filepath
     */
    public AutoSendConfigurationFactory(String filepath) {
        this.filePath = filepath;
    }

    /**
     * Empty constructor, will use default configuration file path
     */
    public AutoSendConfigurationFactory() {
    }

    /**
     * Read configuration file and generate configuration bean
     * @return
     * @throws IOException
     */
    public AutoSendConfiguration getConfiguration() throws Exception {

        InputStream fis;
        BufferedReader br;
        String line;
        String[] keyValue;
        int lineNb = 0;

        fis = new FileInputStream(this.filePath);

        br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));

        AutoSendConfiguration configuration = new AutoSendConfiguration();

        while ((line = br.readLine()) != null) {
            lineNb++;
            if (!line.startsWith("#")){
                keyValue = line.split("=");
                if (keyValue.length != 2)
                    throw new RuntimeException("error in configuration file, line  #" + lineNb );

                if (line.startsWith(WALLET_SOURCE_URL_KEY)){

                    configuration.setSourceURL(keyValue[1]);

                } else if (line.startsWith(WALLET_DESTINATION_TRESHOLD_KEY)){

                    configuration.setBalanceTreshold(Integer.parseInt(keyValue[1]));

                } else if (line.startsWith(WALLET_DESTINATION_URL_KEY)){

                    configuration.addDestinationURL(keyValue[1]);

                } else if (line.startsWith(BATCH_SIZE_KEY)){

                    configuration.setCoinAmount(Integer.parseInt(keyValue[1]));

                } else if (line.startsWith(AUTOSEND_DELAY)){

                    configuration.setAutosendDelay(Integer.parseInt(keyValue[1]));

                } else if (line.startsWith(WALLET_SOURCE_MINIMUM_BALANCE_KEY)){

                    configuration.setMinimumSourceBalance(Integer.parseInt(keyValue[1]));

                } else throw new RuntimeException("error in configuration file, line  #" + lineNb );
            }
        }


        br.close();

        return configuration;

    }
}
