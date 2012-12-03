CREATE TABLE IF NOT EXISTS"item" ("id" INTEGER,"productId" INTEGER,"barcode" TEXT,"entryTime" LONG, "exitTime" LONG,"deleted" BOOL);
CREATE TABLE IF NOT EXISTS "productGroup" ("id" INTEGER, "name" TEXT, "rootParentId" INTEGER,  "parentId" INTEGER, "MonthSupplyAmount" FLOAT, "MonthSupplyUnit" TEXT);
CREATE TABLE IF NOT EXISTS"storageUnit" ("id" INTEGER, "name" TEXT, "rootParentId" INTEGER,"deleted" BOOL);
CREATE TABLE IF NOT EXISTS"product" ("id" INTEGER , "storageUnitId" INTEGER, "parentId" INTEGER,  "barcode" TEXT, "MonthSupply" INTEGER, "sizeAmount" FLOAT, "sizeUnit" TEXT, "deleted" BOOL, "description" TEXT, "shelfLife" INTEGER, "creationDate" LONG);
CREATE TABLE IF NOT EXISTS "miscStorage" ("id" INTEGER,  "datetime" LONG);