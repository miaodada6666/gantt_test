using my.bookshop from '../db/data-model';
using API_ENTERPRISE_PROJECT_SRV as aep from './external/csn/EnterpriseProjectCreateReadUpdateDelete';

service CatalogService {
  // @readonly
  entity Books as projection on bookshop.Books;
  // @readonly
  entity Authors as projection on bookshop.Authors;
  // @insertonly
  entity Orders as projection on bookshop.Orders;
  entity ProjectEE as SELECT from aep.A_EnterpriseProjectType;
  entity ProjectElementEE as SELECT from aep.A_EnterpriseProjectElementType;
  action set2use(bookId: Integer, state: Integer);
}