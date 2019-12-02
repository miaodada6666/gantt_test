package my.localDevTest;

import java.util.*;

//import com.sap.cloud.sdk.service.prov.api.annotations.*;
// import com.sap.cloud.sdk.service.prov.api.*;
import com.sap.cloud.sdk.service.prov.api.operations.Query;
import com.sap.cloud.sdk.service.prov.api.request.*;
import com.sap.cloud.sdk.service.prov.api.response.*;
import com.sap.cloud.sdk.odatav2.connectivity.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.sdk.s4hana.connectivity.*;
//import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.Supplier;
//import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.enterpriseproject.*;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultEnterpriseProjectService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.enterpriseproject.field.EnterpriseProjectElementField;

import com.sap.cloud.sdk.service.prov.api.operations.Read;

public class CatalogService {

    Logger logger = LoggerFactory.getLogger(CatalogService.class);

    private static final String DESTINATION_NAME = "APIHub"; // Refers to the destination created in Step 6
    private static final String apikey = "wZj492sjmNb3yK8jM9hrB6eVkPTN20z0"; // Replace with your API key

    private ErpConfigContext context = new ErpConfigContext(DESTINATION_NAME);

    @Query(serviceName = "CatalogService", entity = "ProjectEE")
    public QueryResponse queryProjectEE(QueryRequest qryRequest) {

        QueryResponse queryResponse;
        int top = qryRequest.getTopOptionValue();
        int skip = qryRequest.getSkipOptionValue();

        try {
            // Create Map containing request header information
            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("Content-Type", "application/json");
            requestHeaders.put("APIKey", apikey);

            // final List<Supplier> suppliers = new
            // DefaultBusinessPartnerService().getAllSupplier()
            // .withCustomHttpHeaders(requestHeaders).onRequestAndImplicitRequests()
            // .select(Supplier.SUPPLIER, Supplier.SUPPLIER_NAME).top(top >= 0 ? top : 50)
            // .skip(skip >= 0 ? skip : -1).execute(context);
            final List<EnterpriseProject> projects = new DefaultEnterpriseProjectService().getAllEnterpriseProject()
                    .withCustomHttpHeaders(requestHeaders).onRequestAndImplicitRequests()
                    // .select(EnterpriseProject.PROJECT, EnterpriseProject.PROJECT_UUID)
                    .top(top >= 0 ? top : 50).skip(skip >= 0 ? skip : -1).execute(context);
            queryResponse = QueryResponse.setSuccess().setData(projects).response();

        } catch (final ODataException e) {
            logger.error("Error occurred with the Query operation: " + e.getMessage(), e);
            ErrorResponse er = ErrorResponse.getBuilder()
                    .setMessage("Error occurred with the Query operation: " + e.getMessage()).setStatusCode(500)
                    .setCause(e).response();
            queryResponse = QueryResponse.setError(er);
        }

        return queryResponse;
    }

    @Query(serviceName = "CatalogService", entity = "ProjectElementEE")
    public QueryResponse queryProjectElementEE(QueryRequest qryRequest) {

        QueryResponse queryResponse;
        int top = qryRequest.getTopOptionValue();
        int skip = qryRequest.getSkipOptionValue();

        try {
            // Create Map containing request header information
            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("Content-Type", "application/json");
            requestHeaders.put("APIKey", apikey);

            // final List<Supplier> suppliers = new
            // DefaultBusinessPartnerService().getAllSupplier()
            // .withCustomHttpHeaders(requestHeaders).onRequestAndImplicitRequests()
            // .select(Supplier.SUPPLIER, Supplier.SUPPLIER_NAME).top(top >= 0 ? top : 50)
            // .skip(skip >= 0 ? skip : -1).execute(context);
            final List<EnterpriseProjectElement> projectElems = new DefaultEnterpriseProjectService()
                    .getAllEnterpriseProjectElement().withCustomHttpHeaders(requestHeaders)
                    .onRequestAndImplicitRequests()
                    // .select(EnterpriseProject.PROJECT, EnterpriseProject.PROJECT_UUID)
                    .top(top >= 0 ? top : 50).skip(skip >= 0 ? skip : -1).execute(context);
            queryResponse = QueryResponse.setSuccess().setData(projectElems).response();

        } catch (final ODataException e) {
            logger.error("Error occurred with the Query operation: " + e.getMessage(), e);
            ErrorResponse er = ErrorResponse.getBuilder()
                    .setMessage("Error occurred with the Query operation: " + e.getMessage()).setStatusCode(500)
                    .setCause(e).response();
            queryResponse = QueryResponse.setError(er);
        }

        return queryResponse;
    }

    @Query(serviceName = "CatalogService", entity = "ProjectElementEE", sourceEntity = "ProjectEE")
    public QueryResponse queryProjectElementEE_fromProjectEE(QueryRequest qryRequest) {

        // logger.info("Reading customer");
        // DataSourceHandler handler = extensionHelper.getHandler();

        /*
         * Get the EntityMetadata object from the request object. This is required
         * because the executeRead method in the DataSourceHandler expects the list of
         * elements to read, and complex/structured elements are expected to be
         * flattened and given as single elements. EntityMetadata has the method
         * getFlattenedElementNames which does exactly this. The entity name comes from
         * the entityMetadata.
         */
        // ReadResponse rr;
        QueryResponse qr;
        int top = qryRequest.getTopOptionValue();
        int skip = qryRequest.getSkipOptionValue();
        // EntityMetadata entityMetadata = readRequest.getEntityMetadata();
        // EntityData entityData =
        // handler.executeRead(entityMetadata.getName(),readRequest.getKeys(),
        // entityMetadata.getFlattenedElementNames());
        try {
            Map<String, String> reqHeaders = new HashMap<>();
            reqHeaders.put("Content-Type", "application/json");
            reqHeaders.put("APIKey", apikey);
            // final EnterpriseProjectElement projElemEE =
            final UUID projID = UUID.fromString(String.valueOf(qryRequest.getSourceKeys().get("ProjectUUID")));
            final List<EnterpriseProjectElement> projElems = new DefaultEnterpriseProjectService()
                    // .getEnterpriseProjectElementByKey(UUID.fromString(String.valueOf(readRequest.getKeys().get("ProjectUUID"))))
                    .getAllEnterpriseProjectElement()
                    .filter(new EnterpriseProjectElementField<UUID>("ProjectUUID").eq(projID))
                    .withCustomHttpHeaders(reqHeaders).onRequestAndImplicitRequests().top(top >= 0 ? top : 50)
                    .skip(skip >= 0 ? skip : -1).execute(context);
            qr = QueryResponse.setSuccess().setData(projElems).response();
        } catch (final ODataException e) {
            logger.error("Error occurred with the Read operation: " + e.getMessage(), e);
            ErrorResponse er = ErrorResponse.getBuilder()
                    .setMessage("Error occurred with the Read operation: " + e.getMessage()).setStatusCode(500)
                    .setCause(e).response();
            qr = QueryResponse.setError(er);
        }
        return qr;
    }

    @Read(serviceName = "CatalogService", entity = "ProjectEE")
    public ReadResponse readProjectEE(ReadRequest readRequest) {

        // logger.info("Reading customer");
        // DataSourceHandler handler = extensionHelper.getHandler();

        /*
         * Get the EntityMetadata object from the request object. This is required
         * because the executeRead method in the DataSourceHandler expects the list of
         * elements to read, and complex/structured elements are expected to be
         * flattened and given as single elements. EntityMetadata has the method
         * getFlattenedElementNames which does exactly this. The entity name comes from
         * the entityMetadata.
         */
        ReadResponse rr;
        // EntityMetadata entityMetadata = readRequest.getEntityMetadata();
        // EntityData entityData =
        // handler.executeRead(entityMetadata.getName(),readRequest.getKeys(),
        // entityMetadata.getFlattenedElementNames());
        try {
            Map<String, String> reqHeaders = new HashMap<>();
            reqHeaders.put("Content-Type", "application/json");
            reqHeaders.put("APIKey", apikey);
            final EnterpriseProject projEE = new DefaultEnterpriseProjectService()
                    .getEnterpriseProjectByKey(
                            UUID.fromString(String.valueOf(readRequest.getKeys().get("ProjectUUID"))))
                    .withCustomHttpHeaders(reqHeaders).onRequestAndImplicitRequests().execute(context);
            rr = ReadResponse.setSuccess().setData(projEE).response();
        } catch (final ODataException e) {
            logger.error("Error occurred with the Read operation: " + e.getMessage(), e);
            ErrorResponse er = ErrorResponse.getBuilder()
                    .setMessage("Error occurred with the Read operation: " + e.getMessage()).setStatusCode(500)
                    .setCause(e).response();
            rr = ReadResponse.setError(er);
        }

        return rr;
    }

    // @Read(serviceName = "CatalogService", entity = "ProjectElementEE",
    // sourceEntity = "ProjectEE")
    // @Read(serviceName = "CatalogService", entity = "ProjectEE", sourceEntity =
    // "ProjectElementEE")
    // public ReadResponse readProjectElementEE(ReadRequest readRequest) {

    // // logger.info("Reading customer");
    // // DataSourceHandler handler = extensionHelper.getHandler();

    // /*
    // * Get the EntityMetadata object from the request object. This is required
    // because the executeRead method in the DataSourceHandler expects the list of
    // elements to read,
    // * and complex/structured elements are expected to be flattened and given as
    // single elements. EntityMetadata has the method getFlattenedElementNames which
    // does exactly this.
    // * The entity name comes from the entityMetadata.
    // */
    // ReadResponse rr;
    // // EntityMetadata entityMetadata = readRequest.getEntityMetadata();
    // // EntityData entityData =
    // handler.executeRead(entityMetadata.getName(),readRequest.getKeys(),
    // entityMetadata.getFlattenedElementNames());
    // try {
    // Map<String, String> reqHeaders = new HashMap<>();
    // reqHeaders.put("Content-Type", "application/json");
    // reqHeaders.put("APIKey", apikey);
    // // final EnterpriseProjectElement projElemEE =
    // final UUID projID =
    // UUID.fromString(String.valueOf(readRequest.getKeys().get("ProjectUUID")));
    // final List<EnterpriseProjectElement> projElems =
    // new DefaultEnterpriseProjectService()
    // //.getEnterpriseProjectElementByKey(UUID.fromString(String.valueOf(readRequest.getKeys().get("ProjectUUID"))))
    // .getAllEnterpriseProjectElement()
    // .filter(new EnterpriseProjectElementField<UUID>("ProjectUUID").eq(projID))
    // .withCustomHttpHeaders(reqHeaders).onRequestAndImplicitRequests()
    // .execute(context);
    // rr = ReadResponse.setSuccess().setData(projElems).response();
    // } catch (final ODataException e) {
    // logger.error("Error occurred with the Read operation: " + e.getMessage(), e);
    // ErrorResponse er = ErrorResponse.getBuilder()
    // .setMessage("Error occurred with the Read operation: " +
    // e.getMessage()).setStatusCode(500)
    // .setCause(e).response();
    // rr = ReadResponse.setError(er);
    // }
    // return rr;
    // }

    // @Function(serviceName = "CatalogService", Name = "GetSupplier")
    // public OperationResponse getSupplierOfOrder(OperationRequest functionRequest,
    // ExtensionHelper extensionHelper) {
    // OperationResponse opResponse;

    // try {
    // // Retrieve the parameters of the function from the
    // // OperationRequest object
    // Map<String, Object> parameters = functionRequest.getParameters();

    // // Get the DataSourceHandler object from the ExtensionHelper. This is
    // required
    // // to execute operations on the local HANA database
    // DataSourceHandler handler = extensionHelper.getHandler();

    // // Retrieve the Order ID from the request
    // Map<String, Object> ordersKey = new HashMap<String, Object>();
    // ordersKey.put("ID", String.valueOf(parameters.get("OrderID")));

    // List<String> orderElements = Arrays.asList("ID", "SUPPLIER");

    // // Read the Order information from the local HANA database
    // EntityData orderData = handler.executeRead("Orders", ordersKey,
    // orderElements);

    // String supplierID = "";
    // List<Object> supplierList = new ArrayList<Object>();
    // if (orderData != null) {
    // // Retrieve the supplier ID from the order information
    // supplierID = orderData.getElementValue("SUPPLIER").toString();

    // // Create Map containing request header information required for querying the
    // // S/4HANA system
    // Map<String, String> requestHeaders = new HashMap<>();
    // requestHeaders.put("Content-Type", "application/json");
    // requestHeaders.put("APIKey", apikey);

    // // Use SAP S/4HANA Cloud SDK's Virtual Data Model to read the Supplier
    // // information
    // final Supplier supplier = new
    // DefaultBusinessPartnerService().getSupplierByKey(supplierID)
    // .withCustomHttpHeaders(requestHeaders).onRequestAndImplicitRequests()
    // .select(Supplier.SUPPLIER, Supplier.SUPPLIER_NAME).execute(context);

    // // Add the retrieved entity data to a supplier list object
    // supplierList.add(supplier);
    // }

    // // Return an instance of OperationResponse containing the list of supplier
    // data
    // opResponse = OperationResponse.setSuccess().setData(supplierList).response();

    // } catch (Exception e) {
    // logger.error("Error in GetSupplier: " + e.getMessage());
    // // Return an instance of OperationResponse containing the error in
    // // case of failure
    // ErrorResponse errorResponse =
    // ErrorResponse.getBuilder().setMessage(e.getMessage()).setCause(e).response();

    // opResponse = OperationResponse.setError(errorResponse);
    // }
    // return opResponse;
    // }

}
