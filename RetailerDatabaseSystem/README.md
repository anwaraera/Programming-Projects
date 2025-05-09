# Retailer Database System

### Project State:
- Completed database design and functionality using MySQL. Produces required reports and capabilities.

### Text File Contains MySQL commands:
- To create all tables necessary
- To load data into created tables from files
- To create stored procedures that generate the necessary reports and capabilities
- To create triggers to automatically log data into tables when necessary

### The database was designed using the following prompt:
- MyAwesomeRetailer orders its products from various suppliers, but a product can only have one supplier. MyAwesomeRetailer has many customers. The database should store information about customers and their orders. The database may contain prospective customers (are in the database but may not have placed an order). Customers should have the ability to rate products. There is no restriction for a customer to only rating products they have ordered, and therefore some products may be rated by customers who did not buy them. A customer may have placed or can place many orders. An order may contain several products ordered in various amounts, some of them ordered as multipacks.
- The system should be able to generate reports that would help the retailer promote the business (for instance finding products of “possible interest” to the individual customers). The retailer also needs to be able to find which products are selling well, which products don’t sell too well to be used in promotional offers, and which customers are active in order to rewarded them with special offers.
- The retailer keeps the existing inventory in a warehouse. Products in the warehouse are sold either as individual items or as indivisible packs containing several units of the same item. As orders are shipped and the inventory depletes, the certain products or multipacks of products may have to be reordered and restocked. Whenever the inventory for a product or product multipack reaches its reorder levels, it must be reordered from its supplier. The database should also keep track of suppliers. A customer can place an order even if the products are not in stock. A customer order can be shipped when all the products in that order are available; no partial orders should be shipped.
