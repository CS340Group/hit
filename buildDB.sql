CREATE TABLE "item" ("id" INTEGER,"productId" INTEGER,"barcode" TEXT,"entryTime" LONG,"exitTime" LONG,"deleted" BOOL);
CREATE TABLE "productGroup" ("id" INTEGER, "name" TEXT, "rootParentId" INTEGER, "parentId" INTEGER, "3MonthSupplyAmount" FLOAT, "3MonthSupplyUnit" TEXT);
CREATE TABLE "storageUnit" ("id" INTEGER, "name" TEXT, "rootParentId" INTEGER);
CREATE TABLE "item" ("id" INTEGER,"productId" INTEGER,"barcode" TEXT,"entryTime" LONG,"exitTime" LONG,"deleted" BOOL)
CREATE TABLE "product" ("id" INTEGER UNIQUE , "storageUnitId" INTEGER, "parentId" INTEGER, "barcode" TEXT, "3MonthSupply" INTEGER, "sizeAmount" FLOAT, "sizeUnit" TEXT, "deleted" BOOL, "description" TEXT, "shelfLife" INTEGER, "creationDate" LONG)
