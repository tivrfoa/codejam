import math

log = False


def rec(n, d, i, inp, res):
    if d > 0: res.append('(')
    while i < n and d <= inp[i]:
        while i < n and d == inp[i]:
            res.append(str(d))
            i += 1

        if i < n and d < inp[i]:
            i=rec(n,d + 1, i, inp, res)

    if d > 0: res.append(')')
    return i


def algo(s):
    inp = [int(sd) for sd in s]
    res = []
    n = len(inp)
    rec(n, 0, 0, inp, res)
    return ''.join(res)


def inp():
    t = int(input())
    for i in range(1, t + 1):
        s = input()
        res = algo(s)
        print("Case #{}: {}".format(i, res))


if __name__ == '__main__':
    inp()
