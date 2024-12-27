### SOL BASIC RPC 

---

**This is a development build. Currently in Alpha stages. You may run into errors, uncompleted functions, or badly written logic. They will be updated and finished before official release** 
---

SOL Basic RPC is an extension to Sol4K to relive missing functions and operations that are essential to larger scale builds. This hopes to rewrite most RPC functions, wallets, and provide needed tools with quick-ease operations that make large scale applications able to be developed using Java.

> **Sol4K** - https://sol4k.org/

---

## How To Use

> The library uses a lot of features as Sol4K. Most functions and features are used the same to make it ease-of-use case to switch.
> Simple transactions can be sent by using :
>
> ```java
> Connection connection = Connection.createConnection("https://api.testnet.solana.com/", Commitment.CONFIRMED);
> TransferInstruction instruction = new TransferInstruction(account.getWallet().getKeypair().getPublicKey(), one.getWallet().getKeypair().getPublicKey(), LAMPORTS_PER_SOL / 10 );
> TransactionMessage message = TransactionMessage.newMessage(account.getWallet().getKeypair().getPublicKey(), connection.getLatestBlockHash(), instruction);
> VersionedTransaction transaction = new VersionedTransaction(message);
> transaction.sign(account.getWallet().getKeypair());
> String signature = connection.sendTransaction(transaction);
> ```
