#queen.py
#This software is a free software.
#Thus, it is licensed under GNU General Public License.
#Python code to solve N-Queen problem
#generate DIMACS CNF file and call minisat to solve it.
#Usage: python queen.py
#for SAT solver project of CS384, Logics for Computer Science
#Forrest Bao, Nov. 26

import sys,string,os;

n=int(sys.argv[1]);

if (len(sys.argv)<2):
 print "Usage: python queen.py   ";
 print "Or, just: python queen.py ";
 exit();

if (len(sys.argv)<3):
 print "You don't specify output DIMACS CNF file name, use default I/O name- nqueen.cnf result";
 ofilename="nqueen.cnf";
 o2filename="result";

if (len(sys.argv)==3):
 print "Using your DIMACS CNF filename: ",sys.argv[2];
 ofilename=sys.argv[2];

if (len(sys.argv)==4):
 print "Using your result filename: ",sys.argv[3];
 o2filename=sys.argv[3];

ofile= open(ofilename,"w");
o2file= open(o2filename,"w");


str0="c"+str(n)+"-queen problem\nc By Forrest Sheng Bao http://fsbao.net\nc This software is a free software under GNU General Public License\nc assume number means grid\nc true means a queen in this grid\nc false means not queen in this grid\nc each line must has one queen\nc no two queens attach each other\nc so they cannot be true at the same time\n";
ofile.write(str0);

#generator rule
for i in range(0,n):
 str0='';
 for j in range(1,n+1):
  number=str(j+n*i);
  str0=str0+number+" ";
 str0=str0+" 0\n";
 ofile.write(str0);
#end of generator rule

#constraints on rows
for i in range(0,n):
 for j in range(1,n):
   number=j+n*i;
   for l in range(1,n-j+1):
    str0="-"+str(number)+" -"+str(number+l)+" 0\n";
    ofile.write(str0);
#END of constraints on rows

#constraints on columns
for j in range(1,n+1):
 for i in range(0,n):
   number=j+n*i;
   for l in range(1,n-i):
    str0="-"+str(number)+" -"+str(number+n*l)+" 0\n";
    ofile.write(str0);
#END of constrains on columns

#constraints on NW->SE diagonal
# part 1, upper bound triangle
for i in range(0,n-1):
 for j in range(i,n-1):
  number=j+1+n*i;
  for l in range(1,n-j):
    str0="-"+str(number)+" -"+str(number+l*(n+1))+" 0\n";
    ofile.write(str0);

# part 2, lower bound triangle
for i in range(0,n-1):
 for j in range(0,i):
  number=j+1+n*i;
  for l in range(1,n-i):
   str0="-"+str(number)+" -"+str(number+l*(n+1))+" 0\n";
   ofile.write(str0);
#END of constraints on NW->SE diagonal

#constraints on NE->SW diagonal
# part 1, upper bound triangle
for i in range(0,n):
 for j in range(0,n-i):
  number=j+1+n*i;
  for l in range(1,j+1):
   str0="-"+str(number)+" -"+str(number+l*(n-1))+" 0\n";
   ofile.write(str0);

# part 2, lower bound triangle
for i in range(0,n):
 for j in range(n-i,n):
  number=j+1+n*i;
  if (number != n*n ):
   for l in range(1,n-i):
    str0="-"+str(number)+" -"+str(number+l*(n-1))+" 0\n";
    ofile.write(str0);
#END of constraints on NE->SW diagonal

ofile.close();

exe="minisat "+ofilename+" "+o2filename;

os.system(exe);

exe2="cat "+o2filename;
os.system(exe2);
