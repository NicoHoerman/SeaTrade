Company crashes sometime, which causes the exit to not work anymore.
Solution kill via taskmanager

GetCargo and GetHarbour currently only work if connected to a SeaTradeServer

Multiple Ships work, but it is not fully tested.

Commands
Register	register:CompanyName:SeaTradeServerPort:SeaTradeServerEndpoint:CompanyServerPort
            Example input:	register:TestCompany:8150:localhost:8080
GetHarbours:	harbours:
GetCargos:	    cargos:
GetCompany:	    company:Shows name, deposit, list of ships	
instructShip	instruct:harbour:ShipIndex	
                Example input: instruct:halifax:0
Exit	        exit:
