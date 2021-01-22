from kafka import KafkaProducer
import json

def producer(broker='kafka:9092', topic='my-topic'):
    p=KafkaProducer(bootstrap_servers=[broker], value_serializer=lambda x: json.dumps(x).encode('utf-8'))

    def send_and_flush(key: str, value: dict, *args, **kwargs):
        p.send(topic, key=key.encode('utf-8'), value=value, *args, **kwargs)
        p.flush()

    return send_and_flush