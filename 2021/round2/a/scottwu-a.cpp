#include <bits/stdc++.h>

using namespace std;
typedef long long ll;

int N;

int mget (int x, int y)
{
    cout << "M " << x << " " << y << "\n" << flush;
    int r; cin >> r;
    return r;
}

int sget (int x, int y)
{
    cout << "S " << x << " " << y << "\n" << flush;
    int r; cin >> r;
    return r;
}

void gogo()
{
    for (int i = 1; i < N; i++)
    {
        int x = mget (i, N);
        if (x != i)
        {
            sget (i, x);
        }
    }
    cout << "D\n" << flush;
    int r; cin >> r;
    if (!r) exit(0);
}

int main()
{
    int T; cin >> T;
    cin >> N;

    for (int t = 0; t < T; t++)
    {
        //cout << "Case #" << t + 1 << ": ";
        gogo();
    }
}