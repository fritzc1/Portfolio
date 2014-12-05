// File DoubleArraySeqDemonstration.java
// This demonstration program shows how to use the DoubleArraySeq class
// from the edu.colorado.homework.

public class DoubleArraySeqDemonstration
{
   public static void main(String[ ] args)
   {
      DoubleArraySeq s = new DoubleArraySeq(1);
      for (double element = 1.0; element < 11.0; element += 1.0)
         s.addAfter(element);
      System.out.println("sequence s");
      s.print();

      DoubleArraySeq t = s.clone();
      t.start();
      for (int i = 0; i < 4; i++)
         t.advance();
      t.addAfter(11.0); t.addAfter(12.0);
      System.out.println("sequence t after adding 11.0 and 12.0 to s");
      t.print();

      DoubleArraySeq u = t.clone();
      u.start();
      for (int i = 0; i < 5; i++)
         u.advance();
      u.removeCurrent(); u.removeCurrent();
      System.out.println("sequence u after removing 11.0 and 12.0 from t");
      u.print();

      DoubleArraySeq v = s.clone();
      v.start();
      for (int i = 0; i < 4; i++)
         v.advance();
      v.addBefore(11.0); v.addBefore(12.0);
      System.out.println("sequence v after adding 11.0 and 12.0 to s");
      v.print();
      v.trimToSize();
      System.out.println("sequence v after trim");
      v.print();
      v.addAll(s);
      System.out.println("sequence v = v + s");
      v.print();
      
      DoubleArraySeq w;
      w = DoubleArraySeq.concatenation(s,t);
      System.out.println("sequence w = s + t");
      w.print();
      
      DoubleArraySeq x = new DoubleArraySeq();
      System.out.println("sequence x is empty");
      x.print();
      System.out.println("Trying to get anything from x causes an exception\n");
      System.out.printf("%5.2f", x.getCurrent());
   }
}
