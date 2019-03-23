/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lambda;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context; 
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import faasinspector.register;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
/**
 * uwt.lambda_test::handleRequest
 * @author wlloyd
 */
public class ProcessCSV implements RequestHandler<Request, Response>
{
    static String CONTAINER_ID = "/tmp/container-id";
    static Charset CHARSET = Charset.forName("US-ASCII");
    
    public Response handleRequest(S3Event s3event, Context context) {         
      	    //obtaining S3 bucket attributes

        LambdaLogger logger = context.getLogger();
        
        // Register function
        register reg = new register(logger);

        //stamp container with uuid
        Response r = reg.StampContainer();
        
        // *********************************************************************
        // Implement Lambda Function Here
        // *********************************************************************
        String bucketname = null;
        String filename = null;
        int row;
        int col;
        //get of number of numbers store here
        int totalArrayLength = 0;
        //add up numbers and store here
        long total = 0;
        //average numbers and store here
        double avg = 0;
        
	    S3EventNotificationRecord record = s3event.getRecords().get(0);
	    String srcBucket = record.getS3().getBucket().getName();
	    String dstBucket = 	"destinationBucketName";	
	    String srcKey = record.getS3().getObject().getKey(); //srcKey is the file name to be read with absolute path 
                System.out.println("srcKey:"+srcKey);
                AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();         
                S3Object s3Object = s3Client.getObject(new GetObjectRequest(srcBucket, srcKey));	//get object file using source bucket and srcKey name
                InputStream objectData = s3Object.getObjectContent(); //get content of the file
                String text = "";
//        try (Scanner scanner = new Scanner(objectData) //scanning data line by line
//        ) {
//           while (scanner.hasNext()) {
//            text += scanner.nextLine();
//            String[] numline = text.split(",");
//            int i=0;
//            for (i = 0; i < numline.length; i++) 
//            { 
//                totalArrayLength += 1;
//                //total += Integer.parseInt(numline[i]);
//            }
  
  //      }
        totalArrayLength = 10;
        total = 10000;
        avg = total/(float)totalArrayLength;
 //   }

        
        //add a statement to the logger
        logger.log("ProcessCSV bucketname:" +  bucketname + "  filename:" + filename +  "avg­element:" + avg + " total:" + total);
        r.setValue("Bucket:  "+ bucketname +"  filename: "+ filename + " processed.");
        return r;
    }
    // Lambda Function Handler
    public Response handleRequest(Request request, Context context) {
        // Create logger
        LambdaLogger logger = context.getLogger();
        
        // Register function
        register reg = new register(logger);

        //stamp container with uuid
        Response r = reg.StampContainer();
        
        // *********************************************************************
        // Implement Lambda Function Here
        // *********************************************************************
        String bucketname = null;
        String filename = null;
        int row;
        int col;
        //get of number of numbers store here
        int totalArrayLength = 0;
        //add up numbers and store here
        long total = 0;
        //average numbers and store here
        double avg = 0;
        
	    String srcBucket = request.getBucketname();
	    String dstBucket = 	"destinationBucketName";	
	    String srcKey = request.getFilename(); //srcKey is the file name to be read with absolute path 
                System.out.println("srcKey:"+srcKey);
                AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();         
                S3Object s3Object = s3Client.getObject(new GetObjectRequest(srcBucket, srcKey));	//get object file using source bucket and srcKey name
                InputStream objectData = s3Object.getObjectContent(); //get content of the file
                String textToUpload = "";
        try (Scanner scanner = new Scanner(objectData) //scanning data line by line
        ) {
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                totalArrayLength += 1;
                total += Integer.parseInt(scanner.next());
            }
            avg = total/(float)totalArrayLength;
    }

        
        //add a statement to the logger
        logger.log("ProcessCSV bucketname:" +  bucketname + "  filename:" + filename +  "avg­element:" + avg + " total:" + total);
        
        return r;
    }
    
    // int main enables testing function from cmd line
    public static void main (String[] args)
    {
        Context c = new Context() {
            @Override
            public String getAwsRequestId() {
                return "";
            }

            @Override
            public String getLogGroupName() {
                return "";
            }

            @Override
            public String getLogStreamName() {
                return "";
            }

            @Override
            public String getFunctionName() {
                return "";
            }

            @Override
            public String getFunctionVersion() {
                return "";
            }

            @Override
            public String getInvokedFunctionArn() {
                return "";
            }

            @Override
            public CognitoIdentity getIdentity() {
                return null;
            }

            @Override
            public ClientContext getClientContext() {
                return null;
            }

            @Override
            public int getRemainingTimeInMillis() {
                return 0;
            }

            @Override
            public int getMemoryLimitInMB() {
                return 0;
            }

            @Override
            public LambdaLogger getLogger() {
                return new LambdaLogger() {
                    @Override
                    public void log(String string) {
                        System.out.println("LOG:" + string);
                    }
                };
            }
        };
        
        // Create an instance of the class
        ProcessCSV lt = new ProcessCSV();

        String filename = args[0];
        String bucketname = args[1];
        int row = Integer.parseInt(args[2]);
        int col = Integer.parseInt(args[3]);
        
        // Create a request object
        Request req = new Request(filename, bucketname, row, col);
        // Report name to stdout
        System.out.println("cmd-line param filename=" + req.getFilename());
        System.out.println("cmd-line param bucketname=" + req.getBucketname());
        System.out.println("cmd-line param row=" + req.getRow());
        System.out.println("cmd-line param col=" + req.getCol());
        
        // Run the function
        //Response resp = lt.handleRequest(req, c);
        
        // Print out function result
        //System.out.println("function result:" + resp.toString());
    }


}
