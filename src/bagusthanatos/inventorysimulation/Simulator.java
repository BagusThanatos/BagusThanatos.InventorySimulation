/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bagusthanatos.inventorysimulation;


import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author BagusNugroho
 */
public class Simulator {
    private Queue<Event> listCustAr = new LinkedList<Event>();
    private Queue<Event> listOrderArrival = new LinkedList<Event>();
    private double clock;
    private int maxS;
    private int minS;
    private int stock;
    private double holdingCost,totalCost,shortageCost,orderingCost,setupCost,itemCost;
    
    public Simulator(int s, int S){
        this.minS=s;
        this.maxS=S;
    }
    public void init(){
        this.clock=0.0;
        this.stock=this.maxS;
        this.holdingCost=0.0;
        this.orderingCost=0.0;
        this.setupCost=0.0;
        this.shortageCost=0.0;
        this.totalCost=0.0;
        this.itemCost=0.0;
    }
    public void inHoldCost(double i){
        this.holdingCost+=i;
    }
    public void inShortCost(double i){
        this.shortageCost+=1;
    }
    public void inSetupCost(double i){
        this.setupCost+=i;
    }
    public void inItemCost(double i){
        this.itemCost+=i;
    }
    public double hitungHC(int jumMobil,double timeAwal,double timeAkhir){
        return jumMobil*100.0*(timeAkhir-timeAkhir);
    }
    public void reCountCost(){
        this.orderingCost=this.setupCost+this.itemCost;
        this.totalCost=this.orderingCost+this.holdingCost+this.shortageCost;
    }
            
    public void setClock(double c){
        this.clock=c;
    }
    public double getClock(){
        return this.clock;
    }
    
    public void addEvent(Event e){
        if(e.getType()==0) this.listCustAr.add(e);
        else this.listOrderArrival.add(e);
    }
    public Event getNextEvent(){
        Event a,b;
        a=this.listCustAr.peek();
        b=this.listOrderArrival.peek();
        if (a!=null && b!=null) {
            if (a.getTime()>b.getTime()) return this.listCustAr.remove();
            else return this.listOrderArrival.remove();
        }
        else if (a!=null) return this.listCustAr.remove();
        else if (b!=null) return this.listOrderArrival.remove();
        else return null;
    }
    public void checkStock(){
        Customer c= new Customer("-1",this.clock+5+Math.random()*2,this.maxS-this.stock);
        if (this.stock<this.minS) this.addEvent(new Event(c,1,c.getArrivalTime()));
        
    }
    public void restock(int a){
        this.stock+=a;
    }
}
