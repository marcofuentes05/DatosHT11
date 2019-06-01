import networkx as nx
import matplotlib.pyplot as plt
# %matplotlib inline

DG = nx.DiGraph()

archivo = open("guategrafo.txt","r")
for linea in archivo.readlines():
	nodos = linea.split(" ")
	DG.add_node(nodos[0])
	DG.add_node(nodos[1])
	DG.add_weighted_edges_from([(nodos[0],nodos[1],int(nodos[2]))])

continuar = True
while (continuar):
	print("""
		MENU
	1. Ruta mas corta
	2. Centro de grafo
	3. Modificar grafo
	4. Ver grafo
	5. Salir""")
	opcion = input("Que opcion desea?: ")

	if (opcion == "1"):
		ciudad = input("Cual es la ciudad de origen?: ")
		destino = input("Cual es la ciudad de destino?: ")
		predecessors, _ = nx.floyd_warshall_predecessor_and_distance(DG)
		print(nx.reconstruct_path(ciudad, destino, predecessors))

	elif(opcion == "2"):
		centro = nx.center(DG)
		print("El centro del grafo esta en:"+ centro[0])

	elif(opcion == "3"):
		print("""
	1. Reportar trafico
	2. Crear nueva conexion""")
		option = input("Que desea hacer?: ")
		if(option == "1"):
			ciudad1 = input("Indique la ciudad de origen: ")
			ciudad2 = input("Indique la ciudad de destino: ")
			trafico = int(input("Simule con un numero que tanto trafico hay: "))
			DG[ciudad1][ciudad2]["weight"] += trafico

		elif(option == "2"):
			ciudad1 = input("Indique la ciudad de origen: ")
			ciudad2 = input("Indique la ciudad de destino: ")
			distancia = int(input("Indique la distancia entre las ciudades:"))
			DG.add_weighted_edges_from([(ciudad1,ciudad2,distancia)])
	elif(opcion == "4"):
		nx.draw(DG, with_labels = True)
		plt.draw()
		plt.show()

	elif(opcion == "5"):
		print("Adios")
		continuar = False
	else:
		print("Opcion incorrecta")