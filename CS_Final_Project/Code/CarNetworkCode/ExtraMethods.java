import java.util.*;
import java.util.Collections;
public class ExtraMethods{
    public static void main(String args[]){
        
        /*
        //sortAddAsc test code
        ArrayList<Integer> l = new ArrayList<Integer>();
        l.add(0);
        l.add(2);
        l.add(3);
        l.add(5);
        l.add(6);
        l.add(6);
        l.add(9);
        Integer b = new Integer(2);
        sortAddAsc(b,l);
        for(int i=0;i<l.size();i++){
            System.out.print(l.get(i)+", ");
        }
        */
        
        
        
    }
    public static void sortAddAsc(Comparable add, ArrayList list){ //sorted add ascengind
        int ind = recurse(add,list,0,list.size()-1);
        
        int index=ind;
        if(ind>=list.size()){
            list.add(add);
        }
        else{
            while(add.compareTo(list.get(index))==0){
                if(index>=list.size()-1){
                    ind= list.size();
                    break;
                }
                else{
                    index++;
                }
            }
            ind = index;
        
        
            if(index>=list.size()){
                list.add(add);
            }
            else{
                list.add(index,add);
            }
        }
                    System.out.println(index);

    }
    
    private static int recurse(Comparable add, ArrayList list, int left, int right){ //used in sortAddAsc above
        int mid = left + (right - left)/2;
        
        if(add.compareTo(list.get(mid))==1){
            if((right-mid)<=1){
                if(add.compareTo(list.get(right))==1){
                    return right+1;
                }
                else{
                return right;}
            }
            else{
            return recurse(add,list,mid,right);}
        }
        else if(add.compareTo(list.get(mid))==0){
            return mid;
            
        }
        else if(add.compareTo(list.get(mid))==-1){
            if((mid-left)<=1){
                if(add.compareTo(list.get(left))==-1){
                    return left;}
                else{
                    return mid;
                }
            }
            else{
            return recurse(add,list,left,mid);}
        }
        else{
            return -1;}
        
    }

}