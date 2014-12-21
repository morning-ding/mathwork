import java.util.Arrays;


public class AES2{

	/**
	 * @param args
	 * @return 
	 */
	public static int[] table = new int[256]; 
	public static int[] arc_table = new int[256];
	public static int[] inverse_table = new int[256];  
	public static AES2 f = new AES2();
	
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
	
	public int mul(int x, int y){ 
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
    
    public byte[] add(int a1, int b1, int c1, int d1){
    	byte[] add = new byte[8];
    	byte[][] a = new byte[4][8];    	
    	a[0] = f.transback(a1);
    	a[1] = f.transback(b1);
    	a[2] = f.transback(c1);
    	a[3] = f.transback(d1);
    	for(int i = 0 ; i < 8; i++)
    		add[i] = (byte) (a[0][i]^a[1][i]^a[2][i]^a[3][i]);
    	return add;
    }
    
/*	public void AES(int[] input){
   	 final int[][] matrix = { { 2, 3, 1, 1 }, { 1, 2, 3, 1 }, { 1, 1, 2, 3 }, { 3, 1, 1, 2 } };
   	 byte[][] output = new byte[4][8];
   	 int[] transinput = new int[4];
   	 transinput[0] = inverse_table[input[0]];
   	 transinput[1] = inverse_table[input[1]];
   	 transinput[2] = inverse_table[input[2]];
   	 transinput[3] = inverse_table[input[3]];
   	 output[0] = f.add(f.mul(matrix[0][0], transinput[0]), f.mul(matrix[0][1], transinput[1]),
   			 f.mul(matrix[0][2], transinput[2]), f.mul(matrix[0][3], transinput[3]));
   	 output[1] = f.add(f.mul(matrix[1][0], transinput[0]), f.mul(matrix[1][1], transinput[1]),
   			 f.mul(matrix[1][2], transinput[2]), f.mul(matrix[1][3], transinput[3]));
   	 output[2] = f.add(f.mul(matrix[2][0], transinput[0]), f.mul(matrix[2][1], transinput[1]),
   			 f.mul(matrix[2][2], transinput[2]), f.mul(matrix[2][3], transinput[3]));
   	 output[3] = f.add(f.mul(matrix[3][0], transinput[0]), f.mul(matrix[3][1], transinput[1]),
   			 f.mul(matrix[3][2], transinput[2]), f.mul(matrix[3][3], transinput[3]));
   	 System.out.println(Arrays.deepToString(output));
   	 }*/
	
	public static void main(String[] args) {
		int a = (int) (Math.random()*256);
		int b = (int) (Math.random()*256);
		System.out.println(a);
		System.out.println(b);
		
		f.multable();
		
		int m = 0 ;
		int res = 0;
		long start = System.currentTimeMillis();
        for(int i = 0; i < 100000; i++)
        	m = f.mul(a, b);
        System.out.println("m:"+m);
        long end = System.currentTimeMillis();
        System.out.println("time used for 100000 mutiply: " + (end - start) + " ms");
        
        long start1 = System.currentTimeMillis();
        f.inversetable();
        for(int i = 0; i < 100000; i++)
        	res = inverse_table[a];
        System.out.println("res:"+res);
        long end1 = System.currentTimeMillis();
        System.out.println("time used for 100000 reverse: " + (end1 - start1) + " ms");
        
        //Sbox
//        int[] in ={33,23,43,45};
//        f.AES(in);
	}
}
	
