import math

log = False



def algo(n, l_inp):
    if log : print(l_inp)
    l_inp.sort()
    l_p=['C','J']
    l_prev=[0,0]
    res=['']*n
    for sc in l_inp:
        for i in range(2):
            if l_prev[i]<=sc[0]:
                res[sc[2]]=l_p[i]
                l_prev[i] = sc[1]
                break
        else: return 'IMPOSSIBLE'
    return ''.join(res)


def inp():
    t = int(input())
    for i in range(1, t + 1):
        n = int(input())
        l_inp = [None] * n
        for j in range(n):
            l_inp[j] = [int(s) for s in input().split(" ")]
            l_inp[j].append(j)
        res = algo(n, l_inp)
        print("Case #{}: {}".format(i, res))


if __name__ == '__main__':
    inp()
