import os
import matplotlib.pyplot as plt
import pandas as pd

CSV_DIR = "csv"
PLOT_DIR = "plots/params"
PARAMETERS = ["alpha_results.csv", "ants_results.csv", "beta_results.csv", "evaporationRate_results.csv",
              "iterations_results.csv", "pheromoneAmount_results.csv"]

def plot_param_metric(data_dict, param_name, metric_name, ylabel, suffix):
    plt.figure(figsize=(10, 6))
    has_data = False

    for algo_name, df in data_dict.items():
        if param_name in df.columns and metric_name in df.columns:
            plt.plot(df[param_name], df[metric_name], label=algo_name)
            has_data = True
        else:
            print(f"Pominięto {algo_name}, brak kolumn: {param_name} i/lub {metric_name}")

    if has_data:
        plt.xlabel(param_name.capitalize())
        plt.ylabel(ylabel)
        plt.title(f"{ylabel} w zależności od {param_name}")
        plt.legend()
        plt.grid(True)
        plt.tight_layout()
        filename = f"{param_name}_{suffix}.png"
        plt.savefig(os.path.join(PLOT_DIR, filename))
        plt.close()
        print(f"Zapisano: {filename}")

# Przechodzimy przez każdy plik parametru
for param_file in PARAMETERS:
    param_name = param_file.replace("_results.csv", "")
    data = {}

    for algo_folder in os.listdir(CSV_DIR):
        if algo_folder == "AcoAlgorithmMorePheromoneWhenBetterPath" or algo_folder == "AcoPheromoneUpdateBasedOnPathLength":
            continue
        folder_path = os.path.join(CSV_DIR, algo_folder)
        file_path = os.path.join(folder_path, param_file)

        if os.path.isdir(folder_path) and os.path.isfile(file_path):
            try:
                df = pd.read_csv(file_path)
                data[algo_folder] = df
            except Exception as e:
                print(f"Błąd przy wczytywaniu {file_path}: {e}")

    if data:
        plot_param_metric(data, param_name, "average_distance", "Średnia długość ścieżki", "distance")
        plot_param_metric(data, param_name, "average_time", "Średni czas działania (ms)", "time")
    else:
        print(f"Brak danych dla parametru: {param_name}")
