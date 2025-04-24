import os
import csv
import sys
import networkx as nx
import matplotlib.pyplot as plt

# Function to read TSP file with coordinates
def read_tsp_file(path):
    with open(path, 'r') as file:
        lines = file.readlines()

    coord_section_index = lines.index("NODE_COORD_SECTION\n") + 1

    coordinates = {}
    for line in lines[coord_section_index:]:
        if line.strip() == "EOF":
            break
        parts = line.split()
        node = int(parts[0])
        x, y = map(int, parts[1:])
        coordinates[node] = (x, y)

    return coordinates


# Function to visualize TSP cycle
def visualize_tsp_cycle(coordinates, tsp_cycle_nodes, path=None):
    G = nx.Graph()

    for node, (x, y) in coordinates.items():
        G.add_node(node, pos=(x, y))

    pos = nx.get_node_attributes(G, 'pos')

    for node in tsp_cycle_nodes:
        if node not in pos:
            print(f"Warning: Node {node} does not have a position in pos dictionary!")

    plt.figure(figsize=(16, 8))
    nx.draw(G, pos, with_labels=False, node_size=50,
            font_size=6, font_color='black', font_weight='bold',
            node_color='black', edge_color='red', width=2)

    # Draw TSP cycle edges
    tsp_cycle_edges = [(tsp_cycle_nodes[i], tsp_cycle_nodes[i + 1]) for i in range(len(tsp_cycle_nodes) - 1)]
    tsp_cycle_edges.append((tsp_cycle_nodes[-1], tsp_cycle_nodes[0]))  # Add the last edge to complete the cycle
    nx.draw_networkx_edges(G, pos, edgelist=tsp_cycle_edges, edge_color='green', width=2)

    plt.savefig(path)


cycles_data_directory = 'cycles'

if len(sys.argv) < 3:
    print("No filename or path data received!")
    sys.exit(1)

filename = sys.argv[1]
tour = list(map(int, sys.argv[2].split(',')))
# filename = 'xqf131.tsp'
# test = [130, 126, 125, 124, 123, 112, 106, 105, 101, 100, 99, 104, 113, 117, 120, 122, 129, 111, 97, 92, 88, 73, 52, 44, 45, 53, 54, 46, 47, 48, 49, 50, 51, 55, 56, 57, 62, 66, 70, 75, 69, 65, 64, 61, 68, 78, 82, 83, 84, 85, 79, 71, 72, 58, 59, 60, 43, 42, 41, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 18, 16, 15, 14, 13, 11, 5, 0, 6, 7, 1, 2, 8, 9, 3, 10, 23, 22, 21, 20, 19, 24, 17, 12, 4, 63, 67, 74, 76, 77, 80, 81, 86, 87, 91, 93, 98, 107, 108, 114, 118, 115, 119, 116, 121, 128, 127, 109, 110, 102, 103, 96, 95, 94, 89, 90]

data_folder = 'src/data/'
file_path = os.path.join(data_folder, filename)

output_dir = 'Visualizer/cycles'

if not os.path.exists(file_path):
    print(f"Error: {file_path} does not exist.")
    sys.exit(1)

coords = read_tsp_file(file_path)
# Add 1 to every node in tsp_cycle_nodes
cycle_nodes = [node + 1 for node in tour]

output_file = os.path.join(output_dir, f"{os.path.splitext(os.path.basename(filename))[0]}_cycle_plot.png")

visualize_tsp_cycle(coords, cycle_nodes, path=output_file)
