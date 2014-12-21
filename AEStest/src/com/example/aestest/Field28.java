package com.example.aestest;

import java.util.Arrays;


public class Field28 {
	/**
	 * @param args
	 * @return 
	 */
	static int[] table= new int[256];
	public static byte[] a = new byte[8];
	static Field28 f = new Field28();
    public byte[] mul(byte[] m1, byte[] m2){
    	byte[][] m = new byte[8][8];
    	byte[] sum = new byte[8];
    	for (int i = 0; i < sum.length; i++)
    		sum[i] = 0;
    	for(int i = 0; i < m2.length; i++)
    		m[i] = m2;
    	for(int i = 0; i < m1.length; i++){
    		if(m1[i] == 1){
    			int j = 7-i;
    			while(j!=0){
    				m[i] = f.left(m[i]);
    				j--;
    			}
    			for (int k = 0; k < sum.length; k++)
            		sum[k] = (byte) ((sum[k] + m[i][k])%2);
    		}    		
    	}
    	return sum;
    }
    
    public byte[] add(byte[] a1, byte[] b1, byte[] c1, byte[] d1){
    	byte[] add = new byte[8];
    	for(int i = 0 ; i < a1.length; i++)
    		add[i] = (byte) (a1[i]^b1[i]^c1[i]^d1[i]);
    	return add;
    }
    
    public byte[] left(byte[] a){
    	byte[] b = new byte[8];
    	b[0] = a[1];
    	b[1] = a[2];
    	b[2] = a[3];
    	b[3] = (byte) ((a[0]+a[4])%2);
    	b[4] = (byte) ((a[0]+a[5])%2);
    	b[5] = (byte) ((a[0]+a[6])%2);
    	b[6] = a[7];
    	b[7] = a[0];
    	return b;
    }
    
    //二转十
    public static int transforth(byte[] t){
    	int sum = 0;
    	for(int i = 0; i < 8; i++){
    	     if(t[i] != 0)
    	         sum += Math.pow(2,7-i);
    	}
    	return sum;
    }
    
    //十转二
    public static byte[] transback(int a){
    	String result = Integer.toBinaryString(a);
    	byte[] b = new byte[8];
    //	System.out.println(Arrays.toString(b));
    	int end = 7;
    	int t = result.length()-1;
    	while(true){
    	    if (t >= 0){
    	        b[end] = (byte)Integer.parseInt(String.valueOf(result.charAt(t)));
    	        end--;
    	        t--;
    	    }else
    	        break;
    	}
    	return b;
    }
    
    public void inverse_generate_table(){
        for(int i = 1; i < 256; i++){
    	    byte[] a = new byte[8];
    	    a = transback(i);
    	    for(int j = 1; j < 256; j++){
    	        byte[] b= new byte[8];
    	        b = transback(j);
    	        byte[] c= new byte[8];
    	        c = mul(a,b);
    	        if (transforth(c) == 1){
    	            table[i]=j;
    	            break;
    	        }
    	    }
    	}
    }
    
	public String  run() {
		// TODO Auto-generated method stub	
		String ret = new String();
		a = new byte[8];
		byte[] b = new byte[8];
		byte[] m = new byte[8];
		byte[] res = new byte[8];			
		for(int i = 0; i < a.length; i++){
			a[i] = (byte) (Math.random()*2);
			b[i] = (byte) (Math.random()*2);
		}
		ret += "a:"+Arrays.toString(a) + "\n";
		ret += "b:"+Arrays.toString(b)+"\n";
		
		long start = System.currentTimeMillis();
        for(int i = 0; i < 100000; i++)
        	m = f.mul(a, b);
        ret += "m:"+Arrays.toString(m) + "\n";
        long end = System.currentTimeMillis();
        ret += "time used for 100000 mutiply: " + (end - start) + " ms\n";
        
      
        f.inverse_generate_table();
        long s = System.currentTimeMillis();
        for(int i = 0; i < 100000; i++){
        	int ten = f.transforth(a);
        	res = f.transback(table[ten]);
        }
        long e = System.currentTimeMillis();
        ret += "res:"+Arrays.toString(res)+"\n";
        ret += "time used for 100000 transverse: " + (e - s) + " ms\n";
        return ret;
	}
}
