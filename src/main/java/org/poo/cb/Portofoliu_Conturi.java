package org.poo.cb;

import org.poo.cb.Cont;
import org.poo.cb.Portofoliu;

import java.util.ArrayList;
import java.util.List;

public class Portofoliu_Conturi implements Portofoliu<Cont> {
    List<Cont> conturi = new ArrayList<>();
    @Override
    public void add(Cont cont) {
        this.conturi.add(cont);
    }

    @Override
    public void clear() {
        if(this.conturi != null)
            this.conturi.clear();
    }
}
