import math

log = False


def latin_square_check(n, l_inp):
    flags = [None] * 2 * n
    for i in range(2 * n):
        flags[i] = [0] * n
    cr = [0] * n
    cc = [0] * n
    trace = 0
    for i in range(n):
        for j in range(n):
            if i == j: trace += l_inp[i][j]
            if flags[i][l_inp[i][j] - 1] > 0:
                cr[i] = 1
            else:
                flags[i][l_inp[i][j] - 1] = 1
            if flags[n + j][l_inp[i][j] - 1] > 0:
                cc[j] = 1
            else:
                flags[n + j][l_inp[i][j] - 1] = 1
            if l_inp[i][j] == 0:
                cr[i] = 1
    res = (trace, sum(cr), sum(cc))
    return res


def put_insq(n, sq, flags, d, i0, de, flags_mark):
    for i in range(i0, n):
        # if log : print(n,d,de,i)
        if flags[i][d] == 0:
            for k in range(i, n + i):
                j = k % n
                if flags[n + j][d] == 0 and sq[i][j] == 0 and (flags_mark[d][i] == 0 or flags_mark[d][n + j] == 0):
                    # if flags[n+j][d]==0 and sq[i][j]==0 :
                    sq[i][j] = d + 1
                    flags[i][d] = flags[n + j][d] = 1
                    for l in range(n):
                        flags_mark[d][l] = flags_mark[d][n + l] = 0
                    if put_insq(n, sq, flags, d, i + 1, de + 1, flags_mark):
                        break
                    else:
                        sq[i][j] = 0
                        flags[i][d] = flags[n + j][d] = 0
                        flags_mark[d][i] = flags_mark[d][n + j] = de

            else:
                return False
    return True


def algo(n, k):
    if k < n or k > n * n or k == n + 1 or k == n * n - 1 \
            or n == 3 and k not in (3, 6, 9):
        return 'IMPOSSIBLE', None
    else:
        r = int(k / n)
        diag = [r] * n
        if k % n > 0:
            delta = k - r * n
            diag[0] += 1
            delta -= 1
            if delta > 0:
                j = 1
                while delta > 0 and j < n:
                    loc = min(delta, n - diag[j])
                    diag[j] += loc
                    delta -= loc
                    j += 1
            else:
                till = int(n - 1)
                for j in range(1, till, 2):
                    if j < n - 1 and r > 1 and r < n:
                        delta = min(int((r + j) % r), n - r)
                        diag[j] -= delta
                        diag[j + 1] += delta
        if log: print('diag', *diag)
        # generating squre
        flags = [None] * 2 * n
        flags_mark = [None] * n
        for i in range(2 * n):
            flags[i] = [0] * n
        cr = [0] * n

        sq = [None] * n
        for i in range(n):
            flags_mark[i] = [0] * 2 * n
            sq[i] = [0] * n
            sq[i][i] = diag[i]
            flags[i][diag[i] - 1] = 1
            flags[n + i][diag[i] - 1] = 1
            cr[diag[i] - 1] += 1
        if log: print('diag cr', *cr)
        l_diag_sort = [(en[1], en[0]) for en in enumerate(cr) if en[1] > 0]
        l_diag_sort.sort(reverse=True)
        ch = True
        # #  first diagonals
        for tu in l_diag_sort:
            ch &= put_insq(n, sq, flags, tu[1], 0, 0, flags_mark)
            if not ch: print('Error filling diag', tu, *flags_mark[i])

        #  first diagonals
        for i in range(n):
            if cr[i] == 0:
                ch &= put_insq(n, sq, flags, i, 0, 0, flags_mark)
                if not ch: print('Error filling data', i, *flags_mark[i])
    return 'POSSIBLE', sq


def inp():
    t = int(input())
    for i in range(1, t + 1):
        b = 0
        n, k = (int(s) for s in input().split(" "))

        res, squr = algo(n, k)

        if squr:
            check = latin_square_check(n, squr)
            if log: print("Case #{}: {} {} {}".format(i, check[0], check[1], check[2]))
            if check[0] != k or check[1] + check[2] > 0: print(' error')

        print("Case #{}: {}".format(i, res))
        if squr:
            for j in range(n):
                print(*squr[j])


if __name__ == '__main__':
    inp()
