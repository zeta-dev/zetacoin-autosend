package org.zbad405.zetacoin.autosend.jobs;

import org.zbad405.zetacoin.autosend.jobs.configuration.AutoSendConfiguration;
import org.zbad405.zetacoin.autosend.jobs.configuration.AutoSendConfigurationFactory;
import zetatip.jsonrpcclient.ZetacoinException;
import zetatip.jsonrpcclient.ZetacoinJSONRPCClient;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gilles Cadignan
 * Job runner class. Main method to be executed in the packaged jar.
 */
public class ZetacoinAUtosend {

    public static void main(String[] args) {

        try {
            //build configuration from file
            AutoSendConfigurationFactory autoSendConfigurationFactory = new AutoSendConfigurationFactory();
            AutoSendConfiguration config = autoSendConfigurationFactory.getConfiguration();

            ZetacoinJSONRPCClient sourceWallet = new ZetacoinJSONRPCClient(config.getSourceURL());
            List<ZetacoinJSONRPCClient> destinationWallets = new ArrayList<ZetacoinJSONRPCClient>();

            for (String url : config.getDestinationURLs()) {
                destinationWallets.add(new ZetacoinJSONRPCClient(url));
            }


            while (true) {

                for (ZetacoinJSONRPCClient destinationWallet : destinationWallets) {


                    if (sourceWallet.getBalance() > config.getMinimumSourceBalance() &&
                            destinationWallet.getBalance() < config.getBalanceTreshold()) {
                        sourceWallet.sendToAddress(destinationWallet.getNewAddress(), config.getCoinAmount());
                    }

                }

                //wait X minutes
                Thread.sleep(config.getAutosendDelay() * 60 * 1000);
            }
        } catch (InterruptedException e) {
            System.err.println("Autosend failde with error : " + e.getMessage());
        } catch (MalformedURLException e) {
            System.err.println("Autosend failde with error : " + e.getMessage());
        } catch (ZetacoinException e) {
            System.err.println("Autosend failde with error : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Autosend failde with error : " + e.getMessage());
        }


    }
}
