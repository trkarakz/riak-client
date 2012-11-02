package com.sony.client;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.IRiakObject;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.bucket.Bucket;
import com.basho.riak.client.cap.DefaultRetrier;
import com.basho.riak.client.raw.http.HTTPClientConfig;

public class App
{
    public static void main(String[] args) throws RiakException
    { 
        // Riak HTTP client with defaults
//        IRiakClient myDefaultHttpClient = RiakFactory.httpClient();
        // Riak HTTP client using supplied URL
        IRiakClient myHttpClient = RiakFactory.httpClient("http://192.168.56.101:8098/riak");

        
//        // Riak Protocol Buffers client with defaults
//        IRiakClient myDefaultPbClient = RiakFactory.pbcClient();
//        // Riak Protocol Buffers client with supplied IP and Port
//        IRiakClient myPbClient = RiakFactory.pbcClient("172.16.1.34", 8087);

        String myData = "This is my data";
        
        // create a bucket
//        Bucket myBucket = myHttpClient.createBucket("MyBucket").execute();
//        HTTPClientConfig httpClientConfig = HTTPClientConfig.defaults();
//        System.out.println(httpClientConfig.getTimeout());
        
        // fetch TestKey
        Bucket myBucket = myHttpClient.fetchBucket("MyBucket").withRetrier(DefaultRetrier.attempts(2)).execute();
        IRiakObject myObject = myBucket.fetch("TestKey").execute();
        System.out.println("Before saving : " + myObject.getValueAsString());

        // store the key
        myBucket.store("TestKey", myData).execute();
        
        // fetch again
        myObject = myBucket.fetch("TestKey").execute();
        System.out.println("After saving : " + myObject.getValueAsString());
        
//        myDefaultHttpClient.shutdown();
        myHttpClient.shutdown();
//        myDefaultPbClient.shutdown();
//        myPbClient.shutdown();
    }   
}
