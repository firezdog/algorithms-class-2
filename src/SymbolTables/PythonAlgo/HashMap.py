class HashMap:
    """Hash map with linear probing."""
    def __init__(self, size=11):
        self.size = size
        self.list = [None] * size

    def put(self, key, value):
        key_hash = self.make_hash(key)
        self.list[key_hash] = value

    def get(self, key):
        key_hash = self.make_hash(key)
        return self.list[key_hash]

    def make_hash(self, key):
        return hash(key) & 0x7fffffff % self.size

    def __getitem__(self, item):
        return self.get(item)

    def __setitem__(self, key, value):
        return self.put(key, value)


if __name__ == "__main__":
    h = HashMap()
    print(h.size)
    print(h[1])
    h['elephant'] = 'dog'
    print(h['elephant'])
