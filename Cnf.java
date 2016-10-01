public class Cnf{
  public static void main(String[] args){
    int n = 3;

    }
  }

public static void RowAssignment(int n){
  /*
  Column Assignments
  */
  String sub1 = "";
  String sub2 = "";
  for(int j = 1; j <= n; j++){
    for(int i = 1; i <= n; i++){
      if(i == n){
        sub1 += (j-1)*n+i + " 0\n";
        sub2 += -1*((j-1)*n+i) + " 0\n";
      }else{
        sub1 += (j-1)*n+i + " ";
        sub2 += -1*((j-1)*n+i) + " ";
       }
      }
    }
  String result = sub1 + sub2;
  System.out.println(result);
}
