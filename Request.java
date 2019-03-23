/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lambda;

/**
 *
 * @author wlloyd
 */
public class Request {

    private String filename;
    private String bucketname;
    private int row;
    private int col;
    public Request()
    {
        
    }
    public Request(String filename, String bucketname, int row, int col)
    {
        this.filename = filename;
        this.bucketname = bucketname;
        this.row = row;
        this.col = row;  
    }
    public String getFilename()
    {
        return this.filename;
    }
    public void setFilename(String filename)
    {
        this.filename = filename;
    }
    public String getBucketname()
    {
        return this.bucketname;
    }
    public void setBucketname(String bucketname)
    {
        this.bucketname = bucketname;
    }
    public int getRow()
    {
        return this.row;
    }
    public void setRow(int row)
    {
        this.row = row;
    }
    public int getCol()
    {
        return this.col;
    }
    public void setCol(int col)
    {
        this.col = col;
    }  
}
