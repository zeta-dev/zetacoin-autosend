/*
 * Copyright (c) 2013, Mikhail Yevchenko. All rights reserved. PROPRIETARY/CONFIDENTIAL.
 */
package zetatip.jsonrpcclient;

import zetatip.jsonrpcclient.Zetacoin.Transaction;

/**
 *
 * @author Gilles Cadignan
 */
public class SimpleZetacoinPaymentListener implements ZetacoinPaymentListener {

    @Override
    public void block(String blockHash) {
    }

    @Override
    public void transaction(Transaction transaction) {
    }
    
}
