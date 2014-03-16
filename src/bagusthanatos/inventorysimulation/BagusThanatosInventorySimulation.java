/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bagusthanatos.inventorysimulation;


/**
 *
 * @author BagusNugroho
 */
public class BagusThanatosInventorySimulation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        final int sim=4;
        
        Simulator[] s=new Simulator[sim];
        s[0]=new Simulator(20,40);
        s[1]=new Simulator(30,60);
        s[2]=new Simulator(40,70);
        s[3]= new Simulator(10,50);
        double[] avg = {0.0,0.0,0.0,0.0};
        for (int x=1;x<=50;x++){
            System.out.println("Percobaan ke-"+x);
            for(int i=0;i<sim;i++) 
                s[i].init();
            for (int i=0;i<50;i++){
                Customer c;
                double r= 0.5+Math.random()*1;
                int mobil= (int)(Math.random()*20);
                c= new Customer((i+1)+"",r,mobil);
                for (int j=0;j<sim;j++){
                    s[j].addEvent(new Event(c,0,c.getArrivalTime()));
                }
            }
             Event e;
            for (int i=0;i<sim;i++){
                e=s[i].getNextEvent();
                while(e!=null){
                    
                    s[i].inHoldCost(s[i].hitungHC(s[i].getStock(),s[i].getClock(),e.getTime()));
                    Customer c= e.getCustomer();
                   
                    if (s[i].getStock()<c.getJumMobil()) {
                        s[i].inShortCost(s[i].hitungShortageCost(c.jumMobil-s[i].getStock()));
                        s[i].deStock(s[i].getStock());
                        
                    }
                    else {
                        s[i].deStock(c.getJumMobil());
                    }
                    s[i].checkStock();
                    s[i].reCountCost();
                    e=s[i].getNextEvent();
                    s[i].setClock(e.getTime());
                }
                if (s[i].getStock()<s[i].getMaxStock()){
                    s[i].inOrderCost(s[i].hitungOrderCost(s[i].getMaxStock()-s[i].getStock()));
                    s[i].reCountCost();
                }
                if (i==0) System.out.print("Total cost [20,40]:");
                else if (i==1) System.out.print("Total cost [30,60]:");
                else if (i==2) System.out.print("Total cost [40,70]:");
                else if (i==3) System.out.print("Total cost [10,50]:");
                System.out.println(s[i].getTotalCost());
                
            }
            
            for (int i=0;i<sim;i++) avg[i]+=s[i].getTotalCost();
        
        }
    }
    
}
