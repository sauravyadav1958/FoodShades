package com.example.project.utils;

public class Utils {
    public static String restaurants = "[\n" +
            "  {\n" +
            "    \"restaurantId\": 1,\n" +
            "    \"restaurantName\": \"Domino's Pizza\",\n" +
            "    \"address\": \"address\",\n" +
            "    \"imageUrl\": \"https://images.unsplash.com/photo-1513104890138-7c749659a591?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2NjQwMzV8MHwxfHNlYXJjaHwxfHxwaXp6YSUyMHdhbGF8ZW58MHx8fHwxNzI4NzM2NTk2fDA&ixlib=rb-4.0.3&q=80&w=200\",\n" +
            "    \"imageSearchKeyword\": \"Pizza\",\n" +
            "    \"foodList\": [\n" +
            "      {\n" +
            "        \"foodId\": 1,\n" +
            "        \"foodName\": \"Veg Pizza\",\n" +
            "        \"imageUrl\": \"https://images.unsplash.com/photo-1534308983496-4fabb1a015ee?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2NjQwMzV8MHwxfHNlYXJjaHwyfHxwaXp6YSUyMHdhbGF8ZW58MHx8fHwxNzI4NzM2NTk2fDA&ixlib=rb-4.0.3&q=80&w=200\",\n" +
            "        \"imageSearchKeyword\": \"\",\n" +
            "        \"description\": \"random long description for current food, tasty and healthy as well\",\n" +
            "        \"foodPrice\": 100.0,\n" +
            "        \"foodStockQuantity\": 100\n" +
            "      },\n" +
            "      {\n" +
            "        \"foodId\": 2,\n" +
            "        \"foodName\": \"Peproni Pizza\",\n" +
            "        \"imageUrl\": \"https://images.unsplash.com/photo-1571997478779-2adcbbe9ab2f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2NjQwMzV8MHwxfHNlYXJjaHwzfHxwaXp6YSUyMHdhbGF8ZW58MHx8fHwxNzI4NzM2NTk2fDA&ixlib=rb-4.0.3&q=80&w=200\",\n" +
            "        \"imageSearchKeyword\": \"\",\n" +
            "        \"description\": \"random long description for current food, tasty and healthy as well\",\n" +
            "        \"foodPrice\": 150.0,\n" +
            "        \"foodStockQuantity\": 100\n" +
            "      }\n" +
            "    ],\n" +
            "    \"addOnList\": [\n" +
            "      {\n" +
            "        \"addOnId\": 1,\n" +
            "        \"addOnName\": \"Tomato Sauce\",\n" +
            "        \"imageUrl\": \"\",\n" +
            "        \"imageSearchKeyword\": \"\",\n" +
            "        \"description\": \"random long description for current food, tasty and healthy as well\",\n" +
            "        \"addOnPrice\": 5.0,\n" +
            "        \"addOnStockQuantity\": 1000\n" +
            "      },\n" +
            "      {\n" +
            "        \"addOnId\": 2,\n" +
            "        \"addOnName\": \"Origano\",\n" +
            "        \"imageUrl\": \"\",\n" +
            "        \"imageSearchKeyword\": \"\",\n" +
            "        \"description\": \"random long description for current food, tasty and healthy as well\",\n" +
            "        \"addOnPrice\": 5.0,\n" +
            "        \"addOnStockQuantity\": 1000\n" +
            "      }\n" +
            "    ],\n" +
            "    \"customerList\": [\n" +
            "      {\n" +
            "        \"customerId\": 1,\n" +
            "        \"name\": \"vegCustomer\",\n" +
            "        \"address\": \"\",\n" +
            "        \"contactNo\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"customerId\": 2,\n" +
            "        \"name\": \"HybridCustomer\",\n" +
            "        \"address\": \"\",\n" +
            "        \"contactNo\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"customerId\": 3,\n" +
            "        \"name\": \"HybridCustomer\",\n" +
            "        \"address\": \"\",\n" +
            "        \"contactNo\": \"\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"restaurantId\": 2,\n" +
            "    \"restaurantName\": \"McDonald's\",\n" +
            "    \"address\": \"address\",\n" +
            "    \"imageUrl\": \"https://images.unsplash.com/photo-1521305916504-4a1121188589?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2NjQwMzV8MHwxfHNlYXJjaHwxfHxidXJnZXIlMjB3YWxhfGVufDB8fHx8MTcyODgwNTUwMnww&ixlib=rb-4.0.3&q=80&w=200\",\n" +
            "    \"imageSearchKeyword\": \"Burger\",\n" +
            "    \"foodList\": [\n" +
            "      {\n" +
            "        \"foodId\": 3,\n" +
            "        \"foodName\": \"Veg Burger\",\n" +
            "        \"imageUrl\": \"https://images.unsplash.com/photo-1530554764233-e79e16c91d08?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2NjQwMzV8MHwxfHNlYXJjaHwyfHxidXJnZXIlMjB3YWxhfGVufDB8fHx8MTcyODgwNTUwMnww&ixlib=rb-4.0.3&q=80&w=200\",\n" +
            "        \"imageSearchKeyword\": \"\",\n" +
            "        \"description\": \"random long description for current food, tasty and healthy as well\",\n" +
            "        \"foodPrice\": 100.0,\n" +
            "        \"foodStockQuantity\": 100\n" +
            "      },\n" +
            "      {\n" +
            "        \"foodId\": 4,\n" +
            "        \"foodName\": \"American Cheese Burger\",\n" +
            "        \"imageUrl\": \"https://images.unsplash.com/photo-1541544741938-0af808871cc0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2NjQwMzV8MHwxfHNlYXJjaHwzfHxidXJnZXIlMjB3YWxhfGVufDB8fHx8MTcyODgwNTUwMnww&ixlib=rb-4.0.3&q=80&w=200\",\n" +
            "        \"imageSearchKeyword\": \"\",\n" +
            "        \"description\": \"random long description for current food, tasty and healthy as well\",\n" +
            "        \"foodPrice\": 150.0,\n" +
            "        \"foodStockQuantity\": 100\n" +
            "      },\n" +
            "      {\n" +
            "        \"foodId\": 5,\n" +
            "        \"foodName\": \"Maharaja Veg Burger\",\n" +
            "        \"imageUrl\": \"https://images.unsplash.com/photo-1508737027454-e6454ef45afd?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2NjQwMzV8MHwxfHNlYXJjaHw0fHxidXJnZXIlMjB3YWxhfGVufDB8fHx8MTcyODgwNTUwMnww&ixlib=rb-4.0.3&q=80&w=200\",\n" +
            "        \"imageSearchKeyword\": \"\",\n" +
            "        \"description\": \"random long description for current food, tasty and healthy as well\",\n" +
            "        \"foodPrice\": 199.0,\n" +
            "        \"foodStockQuantity\": 100\n" +
            "      },\n" +
            "      {\n" +
            "        \"foodId\": 6,\n" +
            "        \"foodName\": \"Chicken Burger\",\n" +
            "        \"imageUrl\": \"https://images.unsplash.com/photo-1460306855393-0410f61241c7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2NjQwMzV8MHwxfHNlYXJjaHw1fHxidXJnZXIlMjB3YWxhfGVufDB8fHx8MTcyODgwNTUwMnww&ixlib=rb-4.0.3&q=80&w=200\",\n" +
            "        \"imageSearchKeyword\": \"\",\n" +
            "        \"description\": \"random long description for current food, tasty and healthy as well\",\n" +
            "        \"foodPrice\": 150.0,\n" +
            "        \"foodStockQuantity\": 100\n" +
            "      },\n" +
            "      {\n" +
            "        \"foodId\": 7,\n" +
            "        \"foodName\": \"Extra Cheese Burger\",\n" +
            "        \"imageUrl\": \"https://images.unsplash.com/photo-1568901346375-23c9450c58cd?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2NjQwMzV8MHwxfHNlYXJjaHw2fHxidXJnZXIlMjB3YWxhfGVufDB8fHx8MTcyODgwNTUwMnww&ixlib=rb-4.0.3&q=80&w=200\",\n" +
            "        \"imageSearchKeyword\": \"\",\n" +
            "        \"description\": \"random long description for current food, tasty and healthy as well\",\n" +
            "        \"foodPrice\": 150.0,\n" +
            "        \"foodStockQuantity\": 100\n" +
            "      }\n" +
            "    ],\n" +
            "    \"addOnList\": [\n" +
            "      {\n" +
            "        \"addOnId\": 3,\n" +
            "        \"addOnName\": \"Tomato Sauce\",\n" +
            "        \"imageUrl\": \"\",\n" +
            "        \"imageSearchKeyword\": \"\",\n" +
            "        \"description\": \"random long description for current food, tasty and healthy as well\",\n" +
            "        \"addOnPrice\": 5.0,\n" +
            "        \"addOnStockQuantity\": 1000\n" +
            "      },\n" +
            "      {\n" +
            "        \"addOnId\": 4,\n" +
            "        \"addOnName\": \"French Fries\",\n" +
            "        \"imageUrl\": \"\",\n" +
            "        \"imageSearchKeyword\": \"\",\n" +
            "        \"description\": \"random long description for current food, tasty and healthy as well\",\n" +
            "        \"addOnPrice\": 50.0,\n" +
            "        \"addOnStockQuantity\": 1000\n" +
            "      }\n" +
            "    ],\n" +
            "    \"customerList\": [\n" +
            "      {\n" +
            "        \"customerId\": 4,\n" +
            "        \"name\": \"vegCustomer\",\n" +
            "        \"address\": \"\",\n" +
            "        \"contactNo\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"customerId\": 5,\n" +
            "        \"name\": \"HybridCustomer\",\n" +
            "        \"address\": \"\",\n" +
            "        \"contactNo\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"customerId\": 6,\n" +
            "        \"name\": \"HybridCustomer\",\n" +
            "        \"address\": \"\",\n" +
            "        \"contactNo\": \"\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "]";
}
