## Introduction
London Gift Shop (LGS) is a UK-based online store that sells gift-ware. The company has been running online shops for more than 10 years but revenue is not growing in recent years. The LGS marketing team wants to utilize the latest data technologies to understand their customers better in order to develop sales and marketing specific techniques.

As the LGS marketing team lacks IT capabilities and resources, they engaged with Jarvis consulting which offered software and data engineering services. This PoC project helps the LGS marketing by answering specific business questions using various technologies. The SQL file was loaded into the PSQL database which acts as data warehouse for OLAP purposes. The analytics were done on Jupyter notebook using Python. Dataframes from the Pandas library were used to manipulate data along with matplotlib from plotting.

## Project Architecture
![my image](./assets/python.png)

The LGS web app consistes of a Microsoft Azure resource group. Azure's content delivery network (CDN) is used  to handle the front-end stack. The backend/API stack is handled using Azure's API management. The back end is an AKS cluster designed with a microservice architecture for processing and a SQL server for OLTP data. The LGS gets its sample data from the server whoch is then stored into the PSQL database Data analytics were carried out using Jupyter notebook.

## Data Analytics and Wranglin
The jupyter notebook presents the data analytics carried out on the retail data (retail.sql) provided by the LGS team. The following business questions were answered:
- Total Invoice Amount Distribution
- Monthly Placed and Cancelled Orders
- Monthly Sales
- Monthly Sales Growth
- Monthly Active Users
- Monthly New and Existing Users
- RFM Segmentation

RFM segmentation renders 3 major segments:
- Can't Lose - It contains customers who previously made large purchases, but have since stopped. New products similar to their previous purchases should be introduced. A marketing campaign should be tailored to the patrons in this section to increase revenue. Investigation on the reasons of halted puchases should also be emphasiszed upon.
- Hibernating - It includes customers who purchased in the past, but were not major contributors to the revenue. New discounts can be introduced to attract these patrons into making more frequent purchases.
- Champions - Included customers responsible for the most purchases/revenue. Campaigns targetting to maintain their purchase frequency and continuity should be implemented. For instance, gifts, perks etc.

## Improvements
- More business questins could be evaluated.
- More segments could be investigated to increase revenue.
- Outliers could be removed through better cleanup.