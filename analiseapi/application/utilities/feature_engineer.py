# Conteúdo do ficheiro: fraud_utils.py
import pandas as pd
import tensorflow as tf
from datetime import datetime
from sklearn.base import BaseEstimator, TransformerMixin

# --- CLASSE DE DATAS (JÁ EXISTENTE) ---

class DatetimeFeatureEngineer(BaseEstimator, TransformerMixin):
    def fit(self, X, y=None):
        return self
    
    def transform(self, X, y=None) -> pd.DataFrame:
        df = X.copy()
        df_out = pd.DataFrame(index=df.index)
        now = datetime.now()
        
        dt_tx = pd.to_datetime(df['data_transacao'])
        df_out['tx_hora_do_dia'] = dt_tx.dt.hour
        df_out['tx_dia_da_semana'] = dt_tx.dt.dayofweek
        df_out['tx_mes'] = dt_tx.dt.month
        
        df_out['recebedor_idade_conta_dias'] = (now - pd.to_datetime(df['recebedor_conta_aberta_em'])).dt.days
        df_out['pagador_idade_anos'] = ((now - pd.to_datetime(df['pagador_data_nascimento'])).dt.days / 365.25).astype(int)
        df_out['recebedor_idade_anos'] = ((now - pd.to_datetime(df['recebedor_data_nascimento'])).dt.days / 365.25).astype(int)
        
        df_out = df_out.fillna(0)
        return df_out

# --- NOVAS FUNÇÕES (NECESSÁRIAS PARA CARREGAR OS MODELOS) ---
#
# Estas são as funções que o KerasClassifier estava a procurar.
# (Nota: O 'input_shape' será fornecido pelo scikeras no momento certo)

def build_binary_model(input_shape):
    model = tf.keras.models.Sequential()
    model.add(tf.keras.layers.Dense(128, input_shape=input_shape, activation='relu'))
    model.add(Dropout(0.5))
    model.add(tf.keras.layers.Dense(64, activation='relu'))
    model.add(Dropout(0.3))
    model.add(tf.keras.layers.Dense(32, activation='relu'))
    model.add(Dropout(0.2))
    model.add(tf.keras.layers.Dense(1, activation='sigmoid')) # Saída binária
    
    model.compile(optimizer=tf.keras.optimizers.Adam(learning_rate=0.001), 
                  loss='binary_crossentropy', 
                  metrics=['accuracy'])
    return model

def build_multiclass_model(input_shape, num_classes):
    model = tf.keras.models.Sequential()
    model.add(tf.keras.layers.Dense(128, input_shape=input_shape, activation='relu'))
    model.add(Dropout(0.5))
    model.add(tf.keras.layers.Dense(64, activation='relu'))
    model.add(Dropout(0.3))
    model.add(tf.keras.layers.Dense(32, activation='relu'))
    model.add(Dropout(0.2))
    model.add(tf.keras.layers.Dense(num_classes, activation='softmax')) # Saída multiclasse
    
    model.compile(optimizer=tf.keras.optimizers.Adam(learning_rate=0.001), 
                  loss='sparse_categorical_crossentropy', 
                  metrics=['accuracy'])
    return model