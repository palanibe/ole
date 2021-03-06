/*
 * Copyright 2008-2009 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl2.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ole.module.purap.document.validation.impl;

import org.kuali.ole.module.purap.PurapConstants;
import org.kuali.ole.module.purap.PurapKeyConstants;
import org.kuali.ole.module.purap.businessobject.PurApItem;
import org.kuali.ole.module.purap.document.PurchasingDocument;
import org.kuali.ole.sys.document.validation.GenericValidation;
import org.kuali.ole.sys.document.validation.event.AttributedDocumentEvent;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.List;

public class PurchasingReceivingValidation extends GenericValidation {

    public boolean validate(AttributedDocumentEvent event) {
        boolean valid = true;

        PurchasingDocument purDoc = (PurchasingDocument) event.getDocument();

        if (!purDoc.isReceivingDocumentRequiredIndicator()) {
            return true;
        }

        GlobalVariables.getMessageMap().clearErrorPath();
        //GlobalVariables.getMessageMap().addToErrorPath(OLEPropertyConstants.DOCUMENT);

        List<PurApItem> items = purDoc.getItems();

        boolean containsQtyTypeItem = false;
        for (PurApItem item : items) {
            if (item.getItemType().isQuantityBasedGeneralLedgerIndicator()) {
                containsQtyTypeItem = true;
                break;
            }
        }

        if (!containsQtyTypeItem) {
            GlobalVariables.getMessageMap().putError(PurapConstants.ITEM_TAB_ERROR_PROPERTY, PurapKeyConstants.ERROR_RECEIVING_REQUIRED);
            valid &= false;
        }

        GlobalVariables.getMessageMap().clearErrorPath();

        return valid;
    }

}
