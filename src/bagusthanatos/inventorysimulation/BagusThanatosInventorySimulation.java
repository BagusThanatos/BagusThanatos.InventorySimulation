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
        Simulator[] s=new Simulator[4];
        s[0]=new Simulator(20,40);
        s[1]=new Simulator(30,60);
        s[2]=new Simulator(40,70);
        s[3]= new Simulator(10,50);
        double[] avg = {0.0,0.0,0.0,0.0};
        for (int x=1;x<=50;x++){
            System.out.println("Percobaan ke-"+x);
            for(int i=0;i<4;i++) {
            s[i].init();
        }
            for (int i=0;i<50;i++){
                Customer c;
                double r= 0.5+Math.random()*1;
                int mobil= 1+(int)Math.random()*10;
                c= new Customer((i+1)+"",r,mobil);
                for (int j=0;j<4;j++){
                    s[j].addEvent(new Event(c,0,c.getArrivalTime()));
                }
            }
        }
    }
    
}
