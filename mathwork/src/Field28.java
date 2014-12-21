import java.util.Arrays;


public class Field28 {

	/**
	 * @param args
	 * @return 
	 */
	static Field28 f = new Field28();
    public void mul(byte[] m1, byte[] m2){
    	//Field28 f = new Field28();
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
    	System.out.println(Arrays.toString(sum));
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
    
    //m是商，q是余数
    public void ojld(byte[] q, byte[] a){
    	byte[] m = new byte[8];
    	int iq = 0;
    	int ia = 0;
    	while(q[iq] == 0){
    		iq++;
    	}
    	while(q[ia] == 0){
    		ia++;
    	}
    	while(iq <= ia){
    	    int j = ia-iq;
    	    m[7-j] = 1;
    	    for(int i = 0; i < a.length-j; i++)
    			a[i] = a[i+j];
    		for(int i = a.length-j; i < a.length; i++)
    			a[i] = 0;
    	    for(int i = 0; i < a.length; i++)
    		    q[i] = (byte) (q[i] ^ a[i]);
    	    iq = 0;
    	    ia = 0;
    	    while(q[iq] == 0){
        		iq++;
        	}
        	while(q[ia] == 0){
        		ia++;
        	}
    	}
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		byte[] a = new byte[8];
		byte[] b = new byte[8];
		long start = System.currentTimeMillis();
		for(int i = 0; i < a.length; i++){
			a[i] = (byte) (Math.random()*2);
			b[i] = (byte) (Math.random()*2);
		}
	//	System.out.println(b[0]);
		//System.out.println(a[0]);
		System.out.println(Arrays.toString(a));
		System.out.println(Arrays.toString(b));
        for(int i = 0; i < 10; i++)
        	f.mul(a, b);
        long end = System.currentTimeMillis();
        System.out.println("time used for 1000000 mutiply: " + (end - start) + " ms");
	}
}
	
