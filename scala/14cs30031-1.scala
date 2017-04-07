val lines=sc.textFile("mean.txt")//read line by line from the src data file cor.txt
val data=lines.map(line=> 
	{
	var row=line.split("\\s+")//splitting each row based on space
	var class_id=row.head.toInt//gettig the class id (rows first element)
	var numbers=new Array[Double](4)//storing the numbers in an array with a last colum containing 1.0
	var i=0.0;
	for(i <- 0 to 2) 
	{
	   numbers(i) = row(i+1).toDouble //taking the rows elements till index 2 

	}
	numbers(3)=1.0//last index assigned the value 1.0 for taking the count of rows
	(class_id,numbers)//making an ordered pair of class_id and numbers array
     }
   )


//function to add the same indexed elements of two arrays...(arr1[0]+arr2[0],arr1[1]+arr2[1],arr1[2]+arr2[2],arr1[3]+arr2[3],.....)
def addArrays(arr1:Array[Double],arr2:Array[Double])=(arr1,arr2) match 
{
  case (var1,var2) => 
  {
  	var sum=new Array[Double](4)
  	var i=0.0;
  	for(i<-0 to 3)
  	{
  		sum(i)=var1(i)+var2(i)
  	}
  	(sum)
  }
}

val res=data.reduceByKey{(x,y)=>addArrays(x,y)}//reduce by key and calling the function defined above
val ans=res.map(mean=>
	{
	var cnt=mean._2(3)
	(mean._1,mean._2(0)/cnt,mean._2(1)/cnt,mean._2(2)/cnt)
	})//storing the mean of all the columns separately along with the class_id at the first.

ans.coalesce(1,true).saveAsTextFile("out.txt")//Note : the output  for all the classes may be broken into different files after opening out.txt that is craeted,so please see both the files for the complete output.
