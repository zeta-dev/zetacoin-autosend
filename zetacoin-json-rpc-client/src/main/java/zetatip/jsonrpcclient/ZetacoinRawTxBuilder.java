/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zetatip.jsonrpcclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

/**
 *
 * @author Gilles Cadignan
 */
public class ZetacoinRawTxBuilder {

    public final Zetacoin zetacoin;

    public ZetacoinRawTxBuilder(Zetacoin zetacoin) {
        this.zetacoin = zetacoin;
    }
    public LinkedHashSet<Zetacoin.TxInput> inputs = new LinkedHashSet();
    public List<Zetacoin.TxOutput> outputs = new ArrayList();

    private class Input extends Zetacoin.BasicTxInput {

        public Input(String txid, int vout) {
            super(txid, vout);
        }

        public Input(Zetacoin.TxInput copy) {
            this(copy.txid(), copy.vout());
        }

        @Override
        public int hashCode() {
            return txid.hashCode() + vout;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (!(obj instanceof Zetacoin.TxInput))
                return false;
            Zetacoin.TxInput other = (Zetacoin.TxInput) obj;
            return vout == other.vout() && txid.equals(other.txid());
        }

    }
    public ZetacoinRawTxBuilder in(Zetacoin.TxInput in) {
        inputs.add(new Input(in.txid(), in.vout()));
        return this;
    }

    public ZetacoinRawTxBuilder in(String txid, int vout) {
        in(new Zetacoin.BasicTxInput(txid, vout));
        return this;
    }

    public ZetacoinRawTxBuilder out(String address, double amount) {
        if (amount <= 0d)
            return this;
        outputs.add(new Zetacoin.BasicTxOutput(address, amount));
        return this;
    }

    public ZetacoinRawTxBuilder in(double value) throws ZetacoinException {
        return in(value, 6);
    }

    public ZetacoinRawTxBuilder in(double value, int minConf) throws ZetacoinException {
        List<Zetacoin.Unspent> unspent = zetacoin.listUnspent(minConf);
        double v = value;
        for (Zetacoin.Unspent o : unspent) {
            if (!inputs.contains(new Input(o))) {
                in(o);
                v = ZetacoinUtil.normalizeAmount(v - o.amount());
            }
            if (v < 0)
                break;
        }
        if (v > 0)
            throw new ZetacoinException("Not enough zetacoins ("+v+"/"+value+")");
        return this;
    }

    private HashMap<String, Zetacoin.RawTransaction> txCache = new HashMap<String, Zetacoin.RawTransaction>();

    private Zetacoin.RawTransaction tx(String txId) throws ZetacoinException {
        Zetacoin.RawTransaction tx = txCache.get(txId);
        if (tx != null)
            return tx;
        tx = zetacoin.getRawTransaction(txId);
        txCache.put(txId, tx);
        return tx;
    }

    public ZetacoinRawTxBuilder outChange(String address) throws ZetacoinException {
        return outChange(address, 0d);
    }

    public ZetacoinRawTxBuilder outChange(String address, double fee) throws ZetacoinException {
        double is = 0d;
        for (Zetacoin.TxInput i : inputs)
            is = ZetacoinUtil.normalizeAmount(is + tx(i.txid()).vOut().get(i.vout()).value());
        double os = fee;
        for (Zetacoin.TxOutput o : outputs)
            os = ZetacoinUtil.normalizeAmount(os + o.amount());
        if (os < is)
            out(address, ZetacoinUtil.normalizeAmount(is - os));
        return this;
    }

    public String create() throws ZetacoinException {
        return zetacoin.createRawTransaction(new ArrayList<Zetacoin.TxInput>(inputs), outputs);
    }
    
    public String sign() throws ZetacoinException {
        return zetacoin.signRawTransaction(create());
    }

    public String send() throws ZetacoinException {
        return zetacoin.sendRawTransaction(sign());
    }

}
