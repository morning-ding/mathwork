package com.example.aestest;



import java.util.Arrays;

public class AES {
	/**
	 * @param args
	 * @return 
	 */
	public static int[] table = new int[256]; 
	public static int[] arc_table = new int[256];
	public static int[] inverse_table = new int[256];  
	public static AES f = new AES();
	
	//对于正表，下标就是生成元g的指数，取值范围为[0, 254]。下标对应元素就是g^k得到的多项式值。取值范围为[1, 255]。
	//对于反表，下标就是g^k得到的多项式值，取值范围为[1, 255]。下标对应的元素就是生成元g的指数，取值范围为[0, 254]。
	public void multable(){
		int i;  
		  
		table[0] = 1;//g^0  
		for(i = 1; i < 255; ++i)//生成元为x + 1  
		{  
		    //下面是m_table[i] = m_table[i-1] * (x + 1)的简写形式  
		    table[i] = (table[i-1] << 1 ) ^ table[i-1];  
		  
		    //最高指数已经到了8，需要模上m(x)  
		    if(table[i] >= 256)  
		    {  
		        table[i] ^= 0x11B;//用到了前面说到的乘法技巧  
		    }  
		} 
	
		for(i = 0; i < 255; ++i)  
		    arc_table[ table[i] ] = i; 
	}
	
	//对于逆表，下标值和下标对应元素的值互为逆元，取值范围都是[1, 255]。k的逆元就是inverse_table[k]。
	public void inversetable(){
		for(int i = 1; i < 256; ++i)//0没有逆元，所以从1开始  
		{  
		    int k = arc_table[i];  
		    k = 255 - k;  
		    k %= 255;//m_table的取值范围为 [0, 254]  
		    inverse_table[i] = table[k];  
		}  
	}
	
	public static int mul(int x, int y){ 
		 if( (x == 0) || (y == 0) )  
		    return 0;
		 else
		    return table[ (arc_table[x] + arc_table[y]) % 255];  
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
    
//    public byte[] add(int a1, int b1, int c1, int d1){
//    	byte[] add = new byte[8];
//    	byte[][] a = new byte[4][8];    	
//    	a[0] = f.transback(a1);
//    	a[1] = f.transback(b1);
//    	a[2] = f.transback(c1);
//    	a[3] = f.transback(d1);
//    	for(int i = 0 ; i < 8; i++)
//    		add[i] = (byte) (a[0][i]^a[1][i]^a[2][i]^a[3][i]);
//    	return add;
//    }
    

	public String run() {
		String ret = new String();
		int[] a = new int[100000];
		int[] b = new int[100000];
		for(int i = 0; i < 100000; i++){
		   a[i] = (int) (Math.random()*256);
		   b[i] = (int) (Math.random()*256);
		}
		ret +="a:"+Arrays.toString(transback(a[0]))+"\n";
		ret += "b:"+Arrays.toString(transback(b[0]))+"\n";
		
		f.multable();
		
		int m = 0 ;
		int res = 0;
		long start = System.currentTimeMillis();
        for(int i = 100000-1; i >= 0; i--)
        	m = mul(a[i],b[i]);       
        long end = System.currentTimeMillis();
        ret += "m:"+Arrays.toString(transback(m))+"\n";
        ret += "time used for 100000 mutiply: " + (end - start) + " ms\n";
        
        long start1 = System.currentTimeMillis();
        f.inversetable();
        for(int i = 0; i < 100000; i++)
        	res = inverse_table[a[i]];        
        long end1 = System.currentTimeMillis();
        ret += "res:"+res +"\n";
        ret += "time used for 100000 reverse: " + (end1 - start1) + " ms\n";
        return ret;
        //Sbox
//        int[] in ={33,23,43,45};
//        f.AES(in);
	}
}
