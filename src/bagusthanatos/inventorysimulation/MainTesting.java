/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bagusthanatos.inventorysimulation;

/**
 *
 * @author BagusThanatos
 */
public class MainTesting {
        public static void main(String[] A){
        double time=0.0;
        Simulator s= new Simulator(20,80);
       
        s.init();
        for (int i=0;i<20;i++){
                
                Customer c;
                double r= 0.5+Math.random()*1;
                time +=r;
                int mobil= (int)(Math.random()*20);
                c= new Customer((i+1)+"",time,mobil);
                s.addEvent(new Event(c, 0,c.getArrivalTime()));
        }
        Event e = s.getNextEvent();
        while (e!=null){
            s.setClock(e.getTime());
            System.out.println("cur time:"+s.getClock());
            System.out.println("Stock awal:"+s.getStock());
            s.inHoldCost(s.hitungHC(s.getStock(),s.getClock(),e.getTime()));
            if (e.getType()==0) {
                Customer c= e.getCustomer();
                System.out.println("Melayani Customer: C-"+c.getName());
                if (s.getStock()<c.getJumMobil()) {
                    System.out.println("C-"+c.getName()+" membeli mobil sebanyak "+c.getJumMobil());
                    s.inShortCost(s.hitungShortageCost(c.getJumMobil()-s.getStock()));
                    c.setJumMobil(c.getJumMobil()-s.getStock());
                    s.deStock(s.getStock());
                    System.out.println("C-"+c.getName()+" memesan mobil sebanyak "+c.getJumMobil());
                    s.addBackLock(c);
                }
                else  {
                       s.deStock(c.getJumMobil());
                       System.out.println("C-"+c.getName()+" membeli mobil sebanyak "+c.getJumMobil());
                }
                     
            }
            else if (e.getType()==1){
                System.out.println("Order tiba : "+e.getCustomer().getJumMobil());
                s.restock(e.getCustomer().getJumMobil());
                s.doBackLog();
            }
            s.checkStock();
            s.reCountCost();
            e = s.getNextEvent();
            System.out.println("Stock akhir:"+s.getStock());
        }
        System.out.println("Total Cost: "+s.getTotalCost());
    }
}
