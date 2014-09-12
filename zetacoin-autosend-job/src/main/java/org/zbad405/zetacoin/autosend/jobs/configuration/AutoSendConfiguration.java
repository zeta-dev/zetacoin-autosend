package org.zbad405.zetacoin.autosend.jobs.configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gilles Cadignan
 */
public class AutoSendConfiguration {
    /**
     * JSON RPC URL of the source wallet
     * format : user:password@host:port
     */
    private String sourceURL;

    /**
     * List of JSON RPC URL of the destination wallets
     * format : user:password@host:port
     */
    private List<String> destinationURLs;

    /**
     * Balance treshold for destination wallets
     */
    private Integer balanceTreshold;

    /**
     * Size of the batch of coins to send to destination wallets
     */
    private Integer coinAmount;

    /**
     * Delay between each balance checking
     */
    private Integer autosendDelay;

    /**
     * Minimum balance for source wallet
     */
    private Integer minimumSourceBalance;

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public List<String> getDestinationURLs() {
        return destinationURLs;
    }

    public void setDestinationURLs(List<String> destinationURLs) {
        this.destinationURLs = destinationURLs;
    }

    public void addDestinationURL(String destinationURL){
        if (this.destinationURLs == null )
            this.destinationURLs = new ArrayList<String>();
        if (!this.destinationURLs.contains(destinationURL))
            this.destinationURLs.add(destinationURL);
    }
    public Integer getBalanceTreshold() {
        return balanceTreshold;
    }

    public void setBalanceTreshold(Integer balanceTreshold) {
        this.balanceTreshold = balanceTreshold;
    }

    public Integer getCoinAmount() {
        return coinAmount;
    }

    public void setCoinAmount(Integer coinAmount) {
        this.coinAmount = coinAmount;
    }

    public Integer getAutosendDelay() {
        return autosendDelay;
    }

    public void setAutosendDelay(Integer autosendDelay) {
        this.autosendDelay = autosendDelay;
    }

    public Integer getMinimumSourceBalance() {
        return minimumSourceBalance;
    }

    public void setMinimumSourceBalance(Integer minimumSourceBalance) {
        this.minimumSourceBalance = minimumSourceBalance;
    }
}
