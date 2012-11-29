CREATE TABLE "item" ("id" INTEGER,"productId" INTEGER,"barcode" TEXT,"entryTime" LONG,"exitTime" LONG,"deleted" BOOL);
CREATE TABLE "productGroup" ("id" INTEGER, "name" TEXT, "rootParentId" INTEGER, "parentId" INTEGER, "3MonthSupplyAmount" FLOAT, "3MonthSupplyUnit" TEXT);
CREATE TABLE "storageUnit" ("id" INTEGER, "name" TEXT, "rootParentId" INTEGER);
