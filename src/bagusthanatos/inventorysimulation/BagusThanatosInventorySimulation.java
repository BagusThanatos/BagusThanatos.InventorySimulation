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
        final int sim=6;
        final int jumPercobaan=50;
        double time;
        Simulator[] s=new Simulator[sim];
        s[0]=new Simulator(20,40);
        s[1]=new Simulator(30,60);
        s[2]=new Simulator(40,70);
        s[3]= new Simulator(10,50);
        s[4]= new Simulator(30,80);
        s[5]= new Simulator(40,60);
        double[] avg = new double[sim];
        for (int i=0; i<sim;i++) avg[i]=0.0;
        for (int x=1;x<=jumPercobaan;x++){
            time=0.0;
            System.out.println("Percobaan ke-"+x);
            for(int i=0;i<sim;i++) s[i].init();
            for (int i=0;i<50;i++){
                
                Customer c;
                double r= 0.5+Math.random()*1;
                time +=r;
                int mobil= (int)(Math.random()*20);
                c= new Customer((i+1)+"",time,mobil);
                for (int j=0;j<sim;j++){
                    s[j].addEvent(new Event(c, 0,c.getArrivalTime()));
                }
            }
            Event e;
            for (int i=0;i<sim;i++){
                e=s[i].getNextEvent();
                while(e!=null){
                    s[i].setClock(e.getTime());
                    s[i].inHoldCost(s[i].hitungHC(s[i].getStock(),s[i].getClock(),e.getTime()));
                    if (e.getType()==0) {
                        Customer c= e.getCustomer();
                        
                        if (s[i].getStock()<c.getJumMobil()) {
                            s[i].inShortCost(s[i].hitungShortageCost(c.getJumMobil()-s[i].getStock()));
                            c.setJumMobil(c.getJumMobil()-s[i].getStock());
                            s[i].deStock(s[i].getStock());
                            s[i].addBackLock(c);
                        }
                        else {
                            s[i].deStock(c.getJumMobil());
                        }
                    }
                    else if (e.getType()==1){
                        s[i].restock(e.getCustomer().getJumMobil());
                        s[i].doBackLog();
                    }
                    s[i].checkStock();
                    s[i].reCountCost();
                    
                    
                    e=s[i].getNextEvent(); 
                    
                }
                
                if (s[i].getStock()<s[i].getMaxStock()){
                    s[i].inOrderCost(s[i].hitungOrderCost(s[i].getMaxStock()-s[i].getStock()));
                    s[i].reCountCost();
                }
                System.out.print("Total cost ");
                if (i==0) System.out.print("[20,40]:");
                else if (i==1) System.out.print("[30,60]:");
                else if (i==2) System.out.print("[40,70]:");
                else if (i==3) System.out.print("[10,50]:");
                else if (i==4) System.out.print("[30,80]:");
                else if (i==5) System.out.print("[40,60]:");
                System.out.println(s[i].getTotalCost());
                
            }
            
            for (int i=0;i<sim;i++) avg[i]+=s[i].getTotalCost();
        
        }
        System.out.println("AVG [20,40]: "+avg[0]/50);
        System.out.println("AVG [30,60]: "+avg[1]/50);
        System.out.println("AVG [40,70]: "+avg[2]/50);
        System.out.println("AVG [10,50]: "+avg[3]/50);
        System.out.println("AVG [30,80]: "+avg[4]/50);
        System.out.println("AVG [40,60]: "+avg[5]/50);
        System.out.println("NOTE: Semua angka di atas dikali dengan 100 ribu rupiah");
    }
    
}
