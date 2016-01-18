package org.kuali.ole.dsng.rest.handler.eholdings;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.kuali.ole.constants.OleNGConstants;
import org.kuali.ole.docstore.engine.service.storage.rdbms.pojo.HoldingsRecord;
import org.kuali.ole.dsng.rest.Exchange;
import org.kuali.ole.dsng.rest.handler.holdings.HoldingsHandler;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by SheikS on 12/31/2015.
 */
public class CancellationEffectiveDateHandler extends HoldingsHandler {

    private final String TYPE = "Cancellation Effective Date";

    @Override
    public Boolean isInterested(String operation) {
        return operation.equals(TYPE);
    }

    @Override
    public void process(JSONObject requestJsonObject, Exchange exchange) {
        HoldingsRecord holdingRecord = (HoldingsRecord) exchange.get(OleNGConstants.HOLDINGS_RECORD);
        String cancellationEffectiveDate = getStringValueFromJsonObject(requestJsonObject, TYPE);
        try{
            Date parsedDate = DOCSTORE_DATE_FORMAT.parse(cancellationEffectiveDate);
            if (holdingRecord.getCancellationEffectiveDate().compareTo(new Timestamp(parsedDate.getTime())) == 0) {
                exchange.add(OleNGConstants.MATCHED_HOLDINGS, holdingRecord);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processDataMappings(JSONObject requestJsonObject, Exchange exchange) {
        JSONArray jsonArrayeFromJsonObject = getJSONArrayeFromJsonObject(requestJsonObject, TYPE);
        List<String> listFromJSONArray = getListFromJSONArray(jsonArrayeFromJsonObject.toString());
        if(CollectionUtils.isNotEmpty(listFromJSONArray)) {
            String cancellationEffectiveDate = listFromJSONArray.get(0);
            HoldingsRecord holdingRecord = (HoldingsRecord) exchange.get(OleNGConstants.HOLDINGS_RECORD);
            try{
                Date parsedDate = DOCSTORE_DATE_FORMAT.parse(cancellationEffectiveDate);
                holdingRecord.setCancellationEffectiveDate(new Timestamp(parsedDate.getTime()));
            } catch(Exception e) {
                e.printStackTrace();
            }
            exchange.add(OleNGConstants.HOLDINGS_RECORD, holdingRecord);
        }
    }
}
