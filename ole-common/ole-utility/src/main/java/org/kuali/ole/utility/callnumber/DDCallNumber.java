package org.kuali.ole.utility.callnumber;


import org.solrmarc.callnum.DeweyCallNumber;

/**
 * Created with IntelliJ IDEA.
 * User: ?
 * Date: 20/2/13
 * Time: 7:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class DDCallNumber extends DeweyCallNumber {
    private static DDCallNumber ourInstance = null;
    public static DDCallNumber getInstance() {
        if (null == ourInstance) {
            ourInstance = new DDCallNumber();
        }
        return ourInstance;
    }
}
