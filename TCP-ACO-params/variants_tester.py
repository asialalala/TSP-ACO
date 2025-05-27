import pandas as pd
import matplotlib.pyplot as plt
import re

df = pd.read_csv("csv/variants/variants.csv")

df["instance"] = df["example"].apply(lambda x: x.split("/")[-1].replace(".tsp", ""))

# Wyciągnij liczby z nazw instancji do sortowania (np. z "xqf131" -> 131)
df["instance_number"] = df["instance"].apply(lambda x: int(re.search(r'\d+', x).group()))

df = df.sort_values("instance_number")

instances = df["instance"].unique()

# 1. Wykres: Średnia odległość
plt.figure(figsize=(10, 6))
for variant in df["variant"].unique():
    subset = df[df["variant"] == variant]
    plt.plot(subset["instance"], subset["avg_distance"], marker='o', label=variant)

plt.title("Średnia wartość rozwiązań dla różnych wariantów ACO")
plt.xlabel("Instancja TSP")
plt.ylabel("Średnia wartość rozwiązania")
plt.xticks(rotation=45)
plt.legend()
plt.tight_layout()
plt.grid(True)
plt.savefig("plots/variants/average_distance_plot.png")

# 2. Wykres: Średni czas
plt.figure(figsize=(10, 6))
for variant in df["variant"].unique():
    subset = df[df["variant"] == variant]
    plt.plot(subset["instance"], subset["avg_time"] / 1000.0, marker='o', label=variant)

plt.title("Średni czas dla różnych wariantów ACO")
plt.xlabel("Instancja TSP")
plt.ylabel("Średni czas (s)")
plt.xticks(rotation=45)
plt.legend()
plt.tight_layout()
plt.grid(True)
plt.savefig("plots/variants/average_time_plot.png")

df_no_opt = pd.read_csv("csv/opt/final.csv")
df_opt = pd.read_csv("csv/opt/final_with_opt.csv")

df_no_opt["instance"] = df_no_opt["example"].apply(lambda x: x.split("/")[-1].replace(".tsp", ""))
df_opt["instance"] = df_opt["example"].apply(lambda x: x.split("/")[-1].replace(".tsp", ""))

df_no_opt["instance_number"] = df_no_opt["instance"].apply(lambda x: int(re.search(r'\d+', x).group()))
df_opt["instance_number"] = df_opt["instance"].apply(lambda x: int(re.search(r'\d+', x).group()))

df_no_opt = df_no_opt.sort_values("instance_number")
df_opt = df_opt.sort_values("instance_number")

labels = df_no_opt["instance"].tolist()



# Wykres średnich odległości dla OPT
plt.figure(figsize=(12, 8))
plt.plot(labels, df_no_opt["avg_distance"], marker="o", label="Bez 2-OPT")
plt.plot(labels, df_opt["avg_distance"], marker="o", label="Z 2-OPT")
plt.xlabel("Instancja TSP")
plt.ylabel("Średnia wartość rozwiązania")
plt.title("Średnia wartość znalezionych rozwiązań - porównanie 2-OPT")
plt.legend()
plt.grid(True)
plt.xticks(rotation=45)
plt.savefig("plots/opt/avg_distance_opt.png")

# Wykres średniego czasu dla OPT
plt.figure(figsize=(12, 8))
plt.plot(labels, df_no_opt["avg_time"], marker="o", label="Bez 2-OPT")
plt.plot(labels, df_opt["avg_time"], marker="o", label="Z 2-OPT")
plt.xlabel("Instancja TSP")
plt.ylabel("Średni czas [s]")
plt.title("Średni czas działania algorytmu - porównanie 2-OPT")
plt.legend()
plt.grid(True)
plt.xticks(rotation=45)
plt.savefig("plots/opt/avg_time_opt.png")
