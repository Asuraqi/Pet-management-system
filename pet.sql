create table commodity_category_table 
(
   category_id          integer                        not null,
   category_name        char(20)                       null,
   category_describe    char(100)                      null,
   constraint PK_COMMODITY_CATEGORY_TABLE primary key (category_id)
);


create table commodity_service_information_table 
(
   commodity_id         integer                        not null,
   category_id          integer                        null,
   commodity_name       char(20)                       null,
   category_name        char(20)                       null,
   commodity_brand                char(20)                       null,
   commodity_price                float(20)                      null,
   commodity_barcode              integer                        null,
   constraint PK_COMMODITY_SERVICE_INFORMATI primary key (commodity_id)
);

create table order_information 
(
   order_id             integer                        not null,
   commodity_id         integer                        null,
   user_id              integer                        null,
   number               integer                        null,
   sale                 float(10)                      null,
   deliver_condition    char(20)                       null,
   staff_id             integer                        null,
   constraint PK_ORDER_INFORMATION primary key  (order_id )
);


create table pet_information_table 
(
   pet_id               integer                        not null,
   user_id              integer                        null,
   pet_nickname             char(20)                       null,
   pet_category                char(20)                       null,
   pet_photo                blob                 null,
   constraint PK_PET_INFORMATION_TABLE primary key (pet_id)
);


create table service_order_information 
(
   pet_id               integer                        not null,
   commodity_id         integer                        not null,
   staff_id             integer                        not null,
   service_id           char(20)                        not null,
   service_time         timestamp                           null,
   service_finish_time  timestamp                           null,
   constraint PK_SERVICE_ORDER_INFORMATION primary key  (service_id)
);


/*==============================================================*/
create table staff_information_table 
(
   staff_id             integer                        not null,
   staff_name           char(20)                       null,
   staff_rank           char(20)                       null,
   staff_pwd            char(20)                       null,
   constraint PK_STAFF_INFORMATION_TABLE primary key (staff_id)
);


create table user_information_table 
(
   user_id              integer                        not null,
   user_name                 char(20)                       null,
   user_phone_number        varchar(40)                        null,
   user_webmail             char(100)                       null,
   constraint PK_USER_INFORMATION_TABLE primary key (user_id)
);
