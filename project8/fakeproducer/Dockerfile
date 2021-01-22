FROM python:3-slim

RUN pip3 install kafka-python bpython
WORKDIR fakeproducer/
COPY /kafka_producer.py .

ENTRYPOINT ["/bin/sh", "-c"]