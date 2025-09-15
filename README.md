# 🚀 Sistema de Prevenção de Fraudes em PIX com IA  

![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)
![Java](https://img.shields.io/badge/Backend-Java-orange)
![Python](https://img.shields.io/badge/Data%20Science-Python-blue)
![Databricks](https://img.shields.io/badge/Platform-Databricks-red)
![MySQL](https://img.shields.io/badge/Database-MySQL-lightblue)
![IA](https://img.shields.io/badge/AI-ML%20Model-green)

---

## 📌 Descrição  
Projeto para **detectar fraudes em transações PIX** usando um modelo de **Inteligência Artificial** integrado a um **simulador de transações em Java**.  
O sistema analisa as operações em tempo real, estima a probabilidade de fraude e decide se deve aprovar ou bloquear a transação.  

---

## 🎯 Objetivos  
- ✅ Detectar fraudes com **alta precisão**.  
- ✅ Reduzir **falsos positivos**.  
- ✅ Integrar IA ao fluxo de transações **antes da liquidação**.  

---

## 🗄️ Dados Públicos do BACEN  
O modelo é inspirado em **dados oficiais** de alta escala, usados para criar uma base sintética realista:  

| 📂 Tabela                           | 📊 Registros   |
|------------------------------------|---------------:|
| 🔑 **chaves_pix**                  | 91.459.726     |
| 🏙️ **transacoes_pix_por_municipio** | 2.873.892      |
| 📈 **estatisticas_transacoes_pix**  | 5.624.483      |

Esses dados permitem definir a **linha de base legítima** e injetar padrões de fraude para treinamento do modelo.  

---

## 🏗️ Arquitetura do Sistema  
- 📊 **Dados** → análise descritiva + geração de dados sintéticos.  
- 🧠 **IA** → modelo de classificação treinado para distinguir *legítimo x fraude*.  
- ⚙️ **Feature Engineering** → valor, frequência, horário, recorrência de chaves, dispositivos, etc.  
- 💻 **Simulador (Java)** → recebe os dados da transação, consulta a IA e retorna score + decisão.  
- 🗄️ **MySQL** → persistência e histórico de transações.  

---

## 📌 Estratégia  
1. 🔎 Explorar dados públicos do BACEN.  
2. 🏗️ Criar base sintética com padrões de fraude e legítimos.  
3. 🧠 Treinar e validar o modelo de IA.  
4. 💻 Integrar ao simulador em Java para prevenção em tempo real.  

---
## 🌲 Estrutura de Branches  
- `main` → versão estável e pronta para produção.  
- `Data-Analysis` → análise, limpeza e geração de dados sintéticos.  
- `AI-Engineering` → desenvolvimento, feature engineering e validação do modelo.  
- `Software-Development` → simulador em Java + integração com banco de dados.  

---




## ▶️ Como Rodar o Projeto


```  à preencher ```

## 🛠️ Tecnologias  

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Python](https://img.shields.io/badge/Python-3776AB?style=flat-square&logo=python&logoColor=white)
![Databricks](https://img.shields.io/badge/Databricks-FF3621?style=flat-square&logo=databricks&logoColor=white)
![Apache Spark](https://img.shields.io/badge/Apache%20Spark-E25A1C?style=flat-square&logo=apachespark&logoColor=white)
![Pandas](https://img.shields.io/badge/Pandas-150458?style=flat-square&logo=pandas&logoColor=white)
![NumPy](https://img.shields.io/badge/NumPy-013243?style=flat-square&logo=numpy&logoColor=white)
![Scikit-learn](https://img.shields.io/badge/Scikit--learn-F7931E?style=flat-square&logo=scikitlearn&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=flat-square&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=github&logoColor=white)
