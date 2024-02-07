package org.poo.cb;

import org.poo.cb.Actiuni;
import org.poo.cb.Portofoliu;

import java.util.ArrayList;
import java.util.List;

public class Portofoliu_Actiuni implements Portofoliu<Actiuni> {
    List<Actiuni> actiuni = new ArrayList<>();
    @Override
    public void add(Actiuni actiune) {
        this.actiuni.add(actiune);
    }

    @Override
    public void clear() {
        if(this.actiuni != null)
            this.actiuni.clear();
    }
}
