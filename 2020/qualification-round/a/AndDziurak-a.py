import math

log = False



def algo(n, b, l_inp):
    flags=[None] *2* n
    for i in range(2*n):
        flags[i]=[0]*n
    cr=[0]*n
    cc=[0]*n
    trace=0
    for i in range(n):
        for j in range(n):
            if i==j: trace+=l_inp[i][j]
            if flags[i][l_inp[i][j]-1]>0:
                cr[i]=1
            else:
                flags[i][l_inp[i][j]-1]=1
            if flags[n+j][l_inp[i][j]-1]>0:
                cc[j]=1
            else:
                flags[n+j][l_inp[i][j]-1]=1
    res=(trace, sum(cr),sum(cc))
    return res


def inp():
    t = int(input())
    for i in range(1, t + 1):
        b=0
        n = int(input())
        l_inp = [None] * n
        for j in range(n):
            l_inp[j]=[int(s) for s in input().split(" ")]

        res = algo(n, b, l_inp)
        print("Case #{}: {} {} {}".format(i, res[0], res[1], res[2]))


if __name__ == '__main__':
    inp()
