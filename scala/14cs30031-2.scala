val lines = sc.textFile("cor.txt")//read line by line from the src data file cor.txt


//using these lines rdd and creating an ordered pair of (xi,yi,xi*yi)
val calcProd=lines.map(line=> 
	{
		var Xval=line.split("\\s+")(0).toDouble
		var Yval=line.split("\\s+")(1).toDouble
		var prod=Xval*Yval
		(Xval,Yval,prod)
	} 
	)
//using lines and creating an ordered pair of their squares (xi^2,yi^2)
val squared=lines.map(line=> 
	{
		var x_2=Math.pow(line.split("\\s+")(0).toDouble,2)
		var y_2=Math.pow(line.split("\\s+")(1).toDouble,2)
		(x_2,y_2)

	}
	)
var prods=lines.map(line=>
{
	var Xval=line.split("\\s+")(0).toDouble
		var Yval=line.split("\\s+")(1).toDouble
		var prod=Xval*Yval
		(prod)

	})

var cnt=calcProd.count()//gives the number of lines in the src file
val sum_all=calcProd.reduce{(x,y)=>
	(x._1+y._1,x._2+y._2,x._3+y._3)}//to get the sum of xi's , sum of yi's and sum of (xi*yi)'s
val sig_sqr_all=squared.reduce{(x,y)=> 
	(x._1+y._1,x._2+y._2)}//to get the sum of all sqaured xi's and yi's

	//pearson coeff=   (n*sum(xi*yi)-sum(xi)*sum(yi))/sqrt(n*sum(xi^2)-(sum(xi))^2)*sqrt(n*sum(yi^2)-(sum(yi))^2)
var num=cnt*(sum_all)._3-(sum_all)._1*(sum_all)._2; //numerator of pearson coeff
var deno=Math.sqrt((sig_sqr_all._1*cnt-Math.pow(sum_all._1,2)))* (Math.sqrt(cnt*sig_sqr_all._2-Math.pow(sum_all._2,2)));//denomenator of coeff
var coeff= num/deno;

//var ans=coeff.toString;
//answer.saveAsTextFile("out-2.txt")

println("\npearson correlation coefficient is " + coeff);
//answer.saveAsTextFile("out-2.txt")